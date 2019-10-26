package com.github.jummes.elytrabooster.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.jummes.elytrabooster.actions.factory.ActionFactory;
import com.github.jummes.elytrabooster.boosts.SimpleBoost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.utils.MessageUtils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class PlayerSimpleBoostEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS_LIST = new HandlerList();
	private boolean cancelled;
	private Player player;

	public PlayerSimpleBoostEvent(ElytraBooster plugin, Player player, SimpleBoost boost) {

		this.player = player;

		plugin.getStatusMap().replace(player, true);
		boost.getBoostActions().forEach(action -> ActionFactory.buildAction(plugin, action, player));
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 20, 1);
		getBoostProcess(plugin, boost).runTaskTimer(plugin, 0, 1);
	}

	private BukkitRunnable getBoostProcess(ElytraBooster plugin, SimpleBoost boost) {
		return new BukkitRunnable() {

			double tempVelocity = boost.getInitialVelocity();
			double d = Math.pow((boost.getFinalVelocity() / boost.getInitialVelocity()),
					(1.0 / boost.getBoostDuration()));

			int counter = 0;

			@Override
			public void run() {

				if (counter == boost.getBoostDuration()) {
					plugin.getStatusMap().replace(player, false);
					this.cancel();
				} else if (!player.isGliding()) {
					plugin.getStatusMap().remove(player);
					this.cancel();
				}

				sendProgressMessage(player, boost, counter);
				boost.getTrail().spawnTrail(player);
				player.setVelocity(player.getLocation().getDirection().normalize().multiply(tempVelocity));

				counter++;
				tempVelocity *= d;
			}
		};
	}

	/**
	 * Sends to the player the progress bar in the action bar
	 * 
	 * @param player
	 * @param portal
	 * @param counter
	 */
	private void sendProgressMessage(Player player, SimpleBoost boost, int counter) {
		int progress = (int) Math.floor((counter / (double) boost.getBoostDuration()) * 30);

		StringBuilder sb = new StringBuilder("");
		sb.append("&a");
		sb.append(MessageUtils.repeat(30 - progress, "|"));
		sb.append("&c");
		sb.append(MessageUtils.repeat(progress, "|"));

		player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
				new ComponentBuilder(
						MessageUtils.color(MessageUtils.color(String.format("&2Boost &6[%s&6]", sb.toString()))))
								.create());
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
		cancelled = isCancelled;
	}

	public Player getPlayer() {
		return player;
	}

}
