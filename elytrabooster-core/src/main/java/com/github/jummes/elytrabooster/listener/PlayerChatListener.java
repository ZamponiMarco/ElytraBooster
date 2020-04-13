package com.github.jummes.elytrabooster.listeners;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.InventoryHolder;

import com.github.jummes.elytrabooster.boosters.Booster;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.gui.settings.StringSettingInventoryHolder;
import com.github.jummes.elytrabooster.managers.boosters.BoosterManager;
import com.github.jummes.elytrabooster.utils.MessageUtils;

public class PlayerChatListener implements Listener {

	private ElytraBooster plugin;

	public PlayerChatListener(ElytraBooster plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Map<HumanEntity, Entry<BoosterManager<?>, Entry<ConfigurationSection, Entry<String, InventoryHolder>>>> settingsMap = StringSettingInventoryHolder
				.getSettingsMap();
		Player p = e.getPlayer();
		if (settingsMap != null && settingsMap.get(p) != null) {
			String key = settingsMap.get(p).getValue().getValue().getKey();
			if (key != null) {
				runModifySyncTask(p, e.getMessage(), settingsMap);
			} else {
				runCreateSyncTask(p, e.getMessage(), settingsMap);
			}
			e.setCancelled(true);
		}
	}

	private void runCreateSyncTask(Player p, String value,
			Map<HumanEntity, Entry<BoosterManager<?>, Entry<ConfigurationSection, Entry<String, InventoryHolder>>>> settingsMap) {
		plugin.getServer().getScheduler().runTask(plugin, () -> {
			BoosterManager<?> manager = settingsMap.get(p).getKey();

			if (!value.equalsIgnoreCase("exit")) {
				if (!manager.getBoostersMap().containsKey(value)) {
					manager.createDefaultBoosterConfiguration(p, value);
					manager.addBooster(value);
					p.sendMessage(MessageUtils.color("&aBooster created, &6ID: &a" + value));
				} else {
					p.sendMessage((MessageUtils.color("&cBooster passed in input is invalid")));
				}
			} else {
				p.sendMessage(MessageUtils.color("&aBooster creation &6&lcancelled"));
			}
			p.openInventory(settingsMap.get(p).getValue().getValue().getValue().getInventory());
			settingsMap.remove(p);
		});
	}

	private void runModifySyncTask(Player p, String value,
			Map<HumanEntity, Entry<BoosterManager<?>, Entry<ConfigurationSection, Entry<String, InventoryHolder>>>> settingsMap) {
		plugin.getServer().getScheduler().runTask(plugin, () -> {
			if (!value.equalsIgnoreCase("exit")) {
				BoosterManager<?> manager = settingsMap.get(p).getKey();
				ConfigurationSection section = settingsMap.get(p).getValue().getKey();
				Booster booster = manager.getBooster(section.getName());
				String key = settingsMap.get(p).getValue().getValue().getKey();
				manager.setParam(booster.getId(), key, value);
				manager.reloadBooster(booster);
				p.sendMessage(MessageUtils.color("&aBooster modified, &6" + key + ": &e" + value));
				p.openInventory(settingsMap.get(p).getValue().getValue().getValue().getInventory());
			} else {
				p.sendMessage(MessageUtils.color("&aThe value &6&lhasn't&a been modified."));
			}
			p.openInventory(settingsMap.get(p).getValue().getValue().getValue().getInventory());
			settingsMap.remove(p);
		});
	}

}
