package com.github.zamponimarco.elytrabooster.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.events.PlayerVerticalBoostEvent;
import com.github.zamponimarco.elytrabooster.managers.boosters.PadManager;

public class PlayerSwapHandItemsListener implements Listener {

	private ElytraBooster plugin;

	public PlayerSwapHandItemsListener(ElytraBooster plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent e) {
		PadManager padManager = plugin.getPadManager();
		if (!e.getPlayer().isGliding() && e.getPlayer().getEquipment().getChestplate() != null
				&& e.getPlayer().getEquipment().getChestplate().getType().equals(Material.ELYTRA)
				&& padManager.getBoostersMap().values().stream()
						.anyMatch(pad -> pad.getCenter().distance(e.getPlayer().getLocation()) <= 1)) {
			padManager.getBoostersMap().values().stream().forEach(pad -> {
				if (pad.getCenter().distance(e.getPlayer().getLocation()) <= 1) {
					pad.cooldown();
					pad.getVisual().onBoost();
					Bukkit.getPluginManager()
							.callEvent(new PlayerVerticalBoostEvent(plugin, e.getPlayer(), pad.getBoost()));
				}
			});
			e.setCancelled(true);
		}
	}

}
