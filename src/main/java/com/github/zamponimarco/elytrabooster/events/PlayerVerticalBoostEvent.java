package com.github.zamponimarco.elytrabooster.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.github.zamponimarco.elytrabooster.actions.factory.ActionFactory;
import com.github.zamponimarco.elytrabooster.boosts.VerticalBoost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class PlayerVerticalBoostEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS_LIST = new HandlerList();
	private boolean cancelled;
	private Player player;

	public PlayerVerticalBoostEvent(ElytraBooster plugin, Player player, VerticalBoost boost) {
		this.player = player;

		
		player.setVelocity(player.getLocation().getDirection().setY(0).multiply(boost.getHorizontalVelocity())
				.add(new Vector(0, boost.getVerticalVelocity(), 0)));
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 20, 1);
		boost.getBoostActions().forEach(action -> ActionFactory.buildAction(plugin, action, player));
		getOpenElytraProcess(plugin, player, boost).runTaskTimer(plugin, 0, 1);
	}

	private BukkitRunnable getOpenElytraProcess(ElytraBooster plugin, Player player, VerticalBoost boost) {
		return new BukkitRunnable() {

			@Override
			public void run() {
				boost.getTrail().spawnTrail(player);
				if (player.getVelocity().normalize().getY() < 0) {
					player.setGliding(true);
					plugin.getStatusMap().put(player, false);
					this.cancel();
				} else if (player.getFallDistance() > 1 && player.isOnGround()) {
					this.cancel();
				}
			}
		};

	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean isCancelled) {
		this.cancelled = isCancelled;
	}

	public Player getPlayer() {
		return player;
	}

}