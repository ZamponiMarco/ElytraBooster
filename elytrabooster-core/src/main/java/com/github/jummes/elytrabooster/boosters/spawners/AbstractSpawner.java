package com.github.jummes.elytrabooster.boosters.spawners;

import org.bukkit.Location;

import com.github.jummes.elytrabooster.boosters.Booster;
import com.github.jummes.elytrabooster.boosts.SimpleBoost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.entityholders.EntityHolder;
import com.github.jummes.elytrabooster.managers.boosters.BoosterManager;

public abstract class AbstractSpawner implements Booster{

	protected ElytraBooster plugin;
	protected String id;
	protected Location center;
	protected double minRadius;
	protected double maxRadius;
	protected int cooldown;
	protected EntityHolder holder;

	protected int spawnTaskNumber;

	public AbstractSpawner(ElytraBooster plugin, String id, Location center, double minRadius, double maxRadius,
			int cooldown, EntityHolder holder) {
		this.plugin = plugin;
		this.id = id;
		this.center = center;
		this.minRadius = minRadius;
		this.maxRadius = maxRadius;
		this.cooldown = cooldown;
		this.holder = holder;
	}
	
	public BoosterManager<?> getDataManager() {
		return plugin.getSpawnerManager();
	}

	public void runBoosterTask() {
		this.spawnTaskNumber = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> spawnEntity(), 0, cooldown)
				.getTaskId();
	}

	private void spawnEntity() {
		holder.spawnEntity(center, minRadius, maxRadius);
	}
	
	public EntityHolder getHolder() {
		return holder;
	}

	public void stopBoosterTask() {
		plugin.getServer().getScheduler().cancelTask(spawnTaskNumber);
		holder.despawnAll();
	}

	public String getId() {
		return id;
	}

	public Location getCenter() {
		return center;
	}

	public double getMinRadius() {
		return minRadius;
	}

	public double getMaxRadius() {
		return maxRadius;
	}

	public void setCenter(Location center) {
		this.center = center;
	}

	public int getCooldown() {
		return cooldown;
	}
	
	public SimpleBoost getBoost() {
		return holder.getBoost();
	}

}
