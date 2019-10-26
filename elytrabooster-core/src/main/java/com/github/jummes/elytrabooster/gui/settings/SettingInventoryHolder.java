package com.github.jummes.elytrabooster.gui.settings;

import java.util.function.Consumer;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import com.github.jummes.elytrabooster.gui.ElytraBoosterInventoryHolder;
import com.github.jummes.elytrabooster.managers.boosters.BoosterManager;

public abstract class SettingInventoryHolder extends ElytraBoosterInventoryHolder {
	protected BoosterManager<?> manager;
	protected ConfigurationSection section;
	protected String key;
	protected Object value;
	protected HumanEntity player;
	protected InventoryHolder holder;

	public SettingInventoryHolder(BoosterManager<?> manager, ConfigurationSection section, String key, Object value,
			HumanEntity player, InventoryHolder holder) {
		this.manager = manager;
		this.section = section;
		this.key = key;
		this.value = value;
		this.player = player;
		this.holder = holder;
	}

	protected Consumer<InventoryClickEvent> getBackConsumer() {
		return e -> player.openInventory(holder.getInventory());
	}

}
