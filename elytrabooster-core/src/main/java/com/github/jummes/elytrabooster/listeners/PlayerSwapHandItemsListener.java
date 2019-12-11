package com.github.jummes.elytrabooster.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.events.PlayerVerticalBoostEvent;
import com.github.jummes.elytrabooster.managers.boosters.PadManager;

public class PlayerSwapHandItemsListener implements Listener {

	private ElytraBooster plugin;

	public PlayerSwapHandItemsListener(ElytraBooster plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent e) {
		PadManager padManager = plugin.getPadManager();
		Player player = e.getPlayer();
		if (player.hasPermission("eb.boosters.boost") && player.getEquipment().getChestplate() != null
				&& player.getEquipment().getChestplate().getType().equals(Material.ELYTRA) && !player.isGliding()) {
			padManager.getBoostersMap().values().stream()
					.filter(pad -> player.getWorld().equals(pad.getCenter().getWorld())
							&& player.getLocation().distance(pad.getCenter()) <= 1)
					.findFirst().ifPresent(pad -> {
						pad.cooldown();
						pad.getVisual().onBoost();
						Bukkit.getPluginManager()
								.callEvent(new PlayerVerticalBoostEvent(plugin, player, pad.getBoost()));
						e.setCancelled(true);
					});
		}
	}

}
