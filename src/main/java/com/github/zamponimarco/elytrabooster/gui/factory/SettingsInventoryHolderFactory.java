package com.github.zamponimarco.elytrabooster.gui.factory;

import org.bukkit.inventory.InventoryHolder;

import com.github.zamponimarco.elytrabooster.gui.ElytraBoosterInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.PadSettingsInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.PortalSettingsInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.SpawnerSettingsInventoryHolder;

public class SettingsInventoryHolderFactory {

	public static ElytraBoosterInventoryHolder buildSettingsInventoryHolder(String boosterString, String boosterId, InventoryHolder holder) {
		if (boosterString.equals("portal")) {
			return new PortalSettingsInventoryHolder(boosterId, holder);
		} else if (boosterString.equals("spawner")) {
			return new SpawnerSettingsInventoryHolder(boosterId, holder);
		} else if (boosterString.equals("pad")) {
			return new PadSettingsInventoryHolder(boosterId, holder);
		}
		return null;
	}

}
