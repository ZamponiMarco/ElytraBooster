package com.github.jummes.elytrabooster.boosters.portals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.jummes.elytrabooster.boosters.Booster;
import com.github.jummes.elytrabooster.boosts.SimpleBoost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.events.FinishedCooldownEvent;
import com.github.jummes.elytrabooster.events.PlayerSimpleBoostEvent;
import com.github.jummes.elytrabooster.managers.boosters.BoosterManager;
import com.github.jummes.elytrabooster.outlines.BlockPortalOutline;
import com.github.jummes.elytrabooster.outlines.PortalOutline;
import com.github.jummes.elytrabooster.outlines.pointsorters.PointSorter;
import com.github.jummes.elytrabooster.settings.Settings;
import com.github.jummes.elytrabooster.utils.MessageUtils;

/**
 * Handles portal boost process
 * 
 */
public abstract class AbstractPortal implements Booster {

	private static final String WARN_MSG = "&4Error with the creation of the portal &6%s &4. Check portals configuration.";

	// Instance variables area ---

	protected ElytraBooster plugin;
	protected AbstractPortal portal;
	protected String id;
	protected Location center;
	protected char axis;
	protected SimpleBoost boost;
	protected PortalOutline outline;
	protected List<UnionPortal> portalsUnion;
	protected int cooldown;
	protected PointSorter sorter;
	protected String measures;

	protected List<Location> points;

	private int outlineTaskNumber;
	private int checkTaskNumber;
	private int currCooldown;
	private int outlineInterval;
	private int checkInterval;

	// ---

	/**
	 * Creates a new abstract portal
	 * 
	 * @param plugin
	 * @param id
	 * @param center
	 * @param axis
	 * @param boost
	 * @param outline
	 * @param portalsUnion
	 * @param cooldown
	 * @param sorter
	 * @param measures
	 */
	public AbstractPortal(ElytraBooster plugin, String id, Location center, char axis, SimpleBoost boost,
			PortalOutline outline, List<UnionPortal> portalsUnion, int cooldown, PointSorter sorter, String measures) {
		super();
		this.plugin = plugin;
		this.portal = this;
		this.id = id;
		this.center = center;
		this.axis = axis >= 120 && axis <= 122 ? axis : 'x';
		this.boost = boost;
		this.outline = outline;
		this.portalsUnion = portalsUnion;
		this.cooldown = cooldown;
		this.sorter = sorter;
		this.measures = measures;

		currCooldown = 0;

		this.outlineInterval = Integer
				.valueOf(plugin.getSettingsManager().getSetting(Settings.PORTAL_OUTLINE_INTERVAL));
		this.checkInterval = Integer.valueOf(plugin.getSettingsManager().getSetting(Settings.PORTAL_CHECK_INTERVAL));
	}

	/**
	 * Initialize measures
	 */
	protected abstract void initMeasures();

	/**
	 * Generates the list of points that represent the outline of a portal
	 * 
	 * @return The list of point of the portal outline
	 */
	protected abstract List<Location> getPoints();

	public BoosterManager<?> getDataManager() {
		return plugin.getPortalManager();
	}

	// ---

	/**
	 * Tests if a location is in the portal area
	 * 
	 * @param location
	 * @param epsilon
	 * @return true if the location is in the portal area
	 */
	protected abstract boolean isInPortalArea(Location location, double epsilon);

	/**
	 * Runs the timer task that checks for users inside the portal and draws the
	 * outline
	 */
	public void runBoosterTask() {
		if (!isActive()) {
			outlineTaskNumber = plugin.getServer().getScheduler()
					.runTaskTimer(plugin, () -> drawOutline(), 1, outlineInterval).getTaskId();
			checkTaskNumber = plugin.getServer().getScheduler()
					.runTaskTimer(plugin, () -> checkPlayersPassing(), 0, checkInterval).getTaskId();
		}
	}

	/**
	 * Stops the portal task
	 */
	public void stopBoosterTask() {
		if (isActive()) {
			outline.eraseOutline(points);
			plugin.getServer().getScheduler().cancelTask(outlineTaskNumber);
			plugin.getServer().getScheduler().cancelTask(checkTaskNumber);
			outlineTaskNumber = 0;
			checkTaskNumber = 0;
		}
	}

	/**
	 * Task that checks if the players are passing throught the portal
	 */
	protected void checkPlayersPassing() {
		if (!onCooldown()) {
			
			/*
			 * Check in order if:
			 *  - player is gliding and is being currently boosted
			 *  - player has correct permission
			 *  - player is in the same world of the portal
			 *  - player is inside portal area
			 */
			plugin.getStatusMap().keySet().stream()
					.filter(player -> !plugin.getStatusMap().get(player) && player.hasPermission("eb.boosters.boost")
							&& player.getWorld().equals(getCenter().getWorld())
							&& isInUnionPortalArea(player.getLocation(), 0))
					.forEach(this::boostPlayer);
		}
	}

	private void boostPlayer(Player player) {
		Bukkit.getPluginManager().callEvent(new PlayerSimpleBoostEvent(plugin, player, (SimpleBoost) boost));
		cooldown();
	}

	/**
	 * Task that draws the outline of the portal
	 */
	protected void drawOutline() {
		if (!onCooldown()) {
			outline.drawOutline(points);
		} else {
			outline.cooldownOutline(points, cooldown, currCooldown);
		}
	}

	/**
	 * Starts and handles the cooldown process
	 */
	protected void cooldown() {
		currCooldown = cooldown;
		BukkitRunnable cooldownProcess = new BukkitRunnable() {
			@Override
			public void run() {
				if (currCooldown > 0) {
					currCooldown--;
				} else {
					Bukkit.getPluginManager().callEvent(new FinishedCooldownEvent(plugin, portal));
					this.cancel();
				}
			}
		};
		if (cooldown > 0)
			cooldownProcess.runTaskTimer(plugin, 0, 1);
	}

	/**
	 * 
	 * @return true if portal is on cooldown
	 */
	protected boolean onCooldown() {
		return currCooldown > 0;
	}

	/**
	 * Returns true if the portal is active
	 * 
	 * @return true if the portal is active
	 */
	public boolean isActive() {
		return checkTaskNumber != 0 && outlineTaskNumber != 0;
	}

	/**
	 * 
	 * @return true if the portal is a union of portal
	 */
	protected boolean isUnion() {
		return !portalsUnion.isEmpty();
	}

	/**
	 * Checks if a location is in the union portal area
	 * 
	 * @param location
	 * @param epsilon
	 * @return true if location is in the union portal area
	 */
	private boolean isInUnionPortalArea(Location location, double epsilon) {
		boolean test = isInPortalArea(location, epsilon);
		for (UnionPortal p : portalsUnion) {
			test = p.isIntersecate() ? test && p.isInPortalArea(location, epsilon)
					: test || p.isInPortalArea(location, epsilon);
		}
		return test;
	}

	/**
	 * Generates the list of points of the outline of a union portal
	 * 
	 * @return the list of the portal union outline points
	 */
	protected List<Location> getUnionPoints() {
		List<Location> unionPoints = new ArrayList<Location>();
		double epsilon = outline instanceof BlockPortalOutline ? 1 : 0.05;
		unionPoints.addAll(getPoints());
		for (UnionPortal portal : portalsUnion) {
			unionPoints.addAll(portal.getPoints());
			if (portal.isIntersecate()) {
				unionPoints = unionPoints.stream().filter(point -> isInUnionPortalArea(point, -epsilon))
						.collect(Collectors.toList());
			} else {
				unionPoints = unionPoints.stream().filter(point -> !isInUnionPortalArea(point, epsilon))
						.collect(Collectors.toList());
			}
		}
		sorter.sort(unionPoints);
		return unionPoints;
	}

	public String warnMessage() {
		return MessageUtils.color(String.format(WARN_MSG, getId()));
	}

	public ElytraBooster getPlugin() {
		return plugin;
	}

	public AbstractPortal getPortal() {
		return portal;
	}

	public String getId() {
		return id;
	}

	public Location getCenter() {
		return center;
	}

	public char getAxis() {
		return axis;
	}

	public PortalOutline getOutline() {
		return outline;
	}

	public List<UnionPortal> getPortalsUnion() {
		return portalsUnion;
	}

	public int getCooldown() {
		return cooldown;
	}

	public int getOutlineTaskNumber() {
		return outlineTaskNumber;
	}

	public int getCheckTaskNumber() {
		return checkTaskNumber;
	}

	public void setCenter(Location center) {
		this.center = center;
	}

	public String getMeasures() {
		return measures;
	}

	public PointSorter getSorter() {
		return sorter;
	}

	public SimpleBoost getBoost() {
		return boost;
	}

	public abstract String getShape();

}
