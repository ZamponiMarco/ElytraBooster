package com.github.jummes.elytrabooster.entities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.jummes.elytrabooster.boosts.Boost;
import com.github.jummes.elytrabooster.boosts.SimpleBoost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.entityholders.EntityHolder;
import com.github.jummes.elytrabooster.events.PlayerSimpleBoostEvent;
import com.github.jummes.elytrabooster.settings.Settings;

public abstract class AbstractEntity {

	private final int CHECK_INTERVAL;
	private final double CHECK_RADIUS = 1.0;

	protected ElytraBooster plugin;
	protected EntityHolder holder;
	protected Location location;
	protected Boost boost;

	private int checkTasknumber;

	public AbstractEntity(ElytraBooster plugin, EntityHolder holder, Location location, Boost boost) {
		this.plugin = plugin;
		this.holder = holder;
		this.location = location;
		this.boost = boost;

		CHECK_INTERVAL = Integer.valueOf(plugin.getSettingsManager().getSetting(Settings.SPAWNER_CHECK_INTERVAL));
		runEntityTask();
	}

	public void runEntityTask() {
		checkTasknumber = plugin.getServer().getScheduler()
				.runTaskTimer(plugin, () -> checkPlayersPassing(), 0, CHECK_INTERVAL).getTaskId();
	}

	private void checkPlayersPassing() {
		
		plugin.getStatusMap().keySet().stream()
				.filter(player -> !plugin.getStatusMap().get(player) && player.hasPermission("eb.boosters.boost")
						&& player.getWorld().equals(location.getWorld())
						&& player.getLocation().distance(location) <= CHECK_RADIUS)
				.forEach(this::boostPlayer);
	}

	private void boostPlayer(Player player) {
		Bukkit.getPluginManager().callEvent(new PlayerSimpleBoostEvent(plugin, player, (SimpleBoost) boost));
		onActivation();
		holderDespawn();
	}

	public void holderDespawn() {
		entityDespawn();
		plugin.getServer().getScheduler().cancelTask(checkTasknumber);
		holder.despawn(this);
	}

	public abstract void onActivation();

	public abstract void spawn();

	public abstract void entityDespawn();

}
