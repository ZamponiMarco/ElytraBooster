package com.github.zamponimarco.elytrabooster.portals.factory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.PortalManager;
import com.github.zamponimarco.elytrabooster.outlines.PortalOutline;
import com.github.zamponimarco.elytrabooster.outlines.factory.PortalOutlineFactory;
import com.github.zamponimarco.elytrabooster.outlines.pointsorters.PointSorter;
import com.github.zamponimarco.elytrabooster.outlines.pointsorters.factory.PointSorterFactory;
import com.github.zamponimarco.elytrabooster.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.portals.CirclePortal;
import com.github.zamponimarco.elytrabooster.portals.RectanglePortal;
import com.github.zamponimarco.elytrabooster.portals.TrianglePortal;
import com.github.zamponimarco.elytrabooster.portals.UnionPortal;
import com.github.zamponimarco.elytrabooster.trails.BoostTrail;
import com.github.zamponimarco.elytrabooster.trails.factory.BoostTrailFactory;

import net.md_5.bungee.api.ChatColor;

/**
 * Manages the creation of portals from a ConfigurationSection of portals.yml
 * 
 * @author Marco
 *
 */
public class PortalFactory {

	/**
	 * Creates a portal from a configuration section
	 * 
	 * @param plugin
	 * @param portalManager
	 * @param portalConfiguration
	 * @return the portal generated by the config section
	 */
	public static AbstractPortal buildPortal(ElytraBooster plugin, PortalManager portalManager,
			ConfigurationSection portalConfiguration) {

		// Portal id
		String id = portalConfiguration.getName();

		// IsBlockOutline
		boolean isBlock = portalConfiguration.getBoolean("isBlockOutline", true);

		// Portal center location
		World world = plugin.getServer().getWorld(portalConfiguration.getString("world"));
		double x = portalConfiguration.getDouble("x");
		double y = portalConfiguration.getDouble("y");
		double z = portalConfiguration.getDouble("z");
		Location center = new Location(world, x, y, z);

		// Portal axis
		char axis = portalConfiguration.getString("axis", "x").charAt(0);

		// Portal initial velocity
		double initialVelocity = portalConfiguration.getDouble("initialVelocity", 3.0);

		// Portal final velocity
		double finalVelocity = portalConfiguration.getDouble("finalVelocity", 1.0);

		// Portal boost duration
		int boostDuration = portalConfiguration.getInt("boostDuration", 30);

		// Portal Outline type
		String outlineType = portalConfiguration.getString("outlineType", "STONE");

		String cooldownType = portalConfiguration.getString("cooldownType", outlineType);

		// Portal shape
		String shape = portalConfiguration.getString("shape", "circle");

		// Portal outline measures
		String measures = portalConfiguration.getString("measures", "10");

		// BoostTrail
		String trailString = portalConfiguration.getString("trail", "firework");
		BoostTrail trail = BoostTrailFactory.buildBoostTrail(trailString);

		// Portal Outline
		PortalOutline outline = PortalOutlineFactory.buildPortalOutline(isBlock, outlineType, cooldownType);

		// Cooldown
		int cooldown = portalConfiguration.getInt("cooldown", 0);

		// Sorter
		String sorterString = portalConfiguration.getString("sorter", "closing");
		PointSorter sorter = PointSorterFactory.buildPointSorter(sorterString, center);

		// Union of portals
		List<UnionPortal> portalsUnion = new ArrayList<UnionPortal>();
		List<String> portalsUnionStringList = portalConfiguration.getStringList("portalsUnion");

		if (portalsUnionStringList != null) {
			try {
				for (int i = 0; i < portalsUnionStringList.size(); i++) {
					String portalString = portalsUnionStringList.get(i);
					String[] portalArray = portalString.split(":");
					String subPortalId = id + "$" + i;
					double unionX = Double.valueOf(portalArray[1]);
					double unionY = Double.valueOf(portalArray[2]);
					double unionZ = Double.valueOf(portalArray[3]);
					switch(axis) {
					case 'x':
						if(unionX != x) throw new IllegalArgumentException("Union portals and main portal axis coord values must be the same");
						break;
					case 'y':
						if(unionY != y) throw new IllegalArgumentException("Union portals and main portal axis coord values must be the same");
						break;
					case 'z':
						if(unionZ != z) throw new IllegalArgumentException("Union portals and main portal axis coord values must be the same");
						break;
					}
					String unionShape = portalArray[0];
					String unionMeasures = portalArray[4];
					boolean intersecate = Boolean.valueOf(portalArray[5]);

					UnionPortal portal = buildUnionPortal(plugin, subPortalId,
							new Location(world, unionX, unionY, unionZ), axis, initialVelocity, finalVelocity,
							boostDuration, outline, new ArrayList<UnionPortal>(), trail, unionShape, cooldown, sorter,
							unionMeasures, intersecate);
					portalsUnion.add(portal);
				}
			} catch (Exception e) {
				portalsUnion.clear();
				Bukkit.getLogger().warning(
						ChatColor.RED + "Portals union creation for portal named " + id + " failed, check portals.yml");
			}
		}

		// Build portal
		return buildPortal(plugin, id, center, axis, initialVelocity, finalVelocity, boostDuration, outline,
				portalsUnion, trail, shape, cooldown, sorter, measures);
	}

	private static AbstractPortal buildPortal(ElytraBooster plugin, String id, Location center, char axis,
			double initialVelocity, double finalVelocity, int boostDuration, PortalOutline outline,
			List<UnionPortal> portalsUnion, BoostTrail trail, String shape, int cooldown, PointSorter sorter,
			String measures) {
		switch (shape) {
		case "square":
			return new RectanglePortal(plugin, id, center, axis, initialVelocity, finalVelocity, boostDuration, outline,
					portalsUnion, trail, cooldown, sorter, measures + ";" + measures);
		case "rectangle":
			return new RectanglePortal(plugin, id, center, axis, initialVelocity, finalVelocity, boostDuration, outline,
					portalsUnion, trail, cooldown, sorter, measures);
		case "triangle":
			return new TrianglePortal(plugin, id, center, axis, initialVelocity, finalVelocity, boostDuration, outline,
					portalsUnion, trail, cooldown, sorter, measures);
		case "circle":
		default:
			return new CirclePortal(plugin, id, center, axis, initialVelocity, finalVelocity, boostDuration, outline,
					portalsUnion, trail, cooldown, sorter, measures);
		}
	}

	private static UnionPortal buildUnionPortal(ElytraBooster plugin, String id, Location center, char axis,
			double initialVelocity, double finalVelocity, int boostDuration, PortalOutline outline,
			List<UnionPortal> portalsUnion, BoostTrail trail, String shape, int cooldown, PointSorter sorter,
			String measures, boolean intersecate) {
		return new UnionPortal(plugin, id, center, axis, initialVelocity, finalVelocity, boostDuration, outline,
				portalsUnion, trail, shape, cooldown, measures, sorter, intersecate);
	}
}