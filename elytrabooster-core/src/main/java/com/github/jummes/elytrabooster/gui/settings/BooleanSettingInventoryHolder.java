package com.github.jummes.elytrabooster.gui.settings;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.jummes.elytrabooster.boosters.Booster;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.managers.boosters.BoosterManager;
import com.github.jummes.elytrabooster.utils.MessageUtils;

public class BooleanSettingInventoryHolder extends SettingInventoryHolder {

	private static final String TRUE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE3NWM4ZTUxYzNkMTA1YmFiNGM3ZGUzM2E3NzA5MzczNjRiNWEwMWMxNWI3ZGI4MmNjM2UxZmU2ZWI5MzM5NiJ9fX0==";
	private static final String FALSE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY3NGQ0ZGI0Y2MzYmU0MWEzNzNkOWVmOWNhYzI3ZTYzNThjNTNmNjQxMTVkMTUwMjQzZjI1YWNmNjRmMmY1MCJ9fX0====";

	public BooleanSettingInventoryHolder(BoosterManager<?> manager, ConfigurationSection section, String key, Object value,
			HumanEntity player, InventoryHolder holder) {
		super(manager, section, key, value, player, holder);
	}

	@Override
	protected void initializeInventory() {
		this.inventory = Bukkit.createInventory(this, 27, MessageUtils.color("&6&lModify &e&l" + key));
		registerClickConsumer(12, getBooleanItem(ElytraBooster.getInstance().getWrapper().skullFromValue(TRUE_HEAD), true), getBooleanConsumer(true));
		registerClickConsumer(14, getBooleanItem(ElytraBooster.getInstance().getWrapper().skullFromValue(FALSE_HEAD), false),
				getBooleanConsumer(false));
		registerClickConsumer(26, getBackItem(), getBackConsumer());
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private Consumer<InventoryClickEvent> getBooleanConsumer(boolean value) {
		return e -> {
			Booster booster = manager.getBooster(section.getName());
			manager.setParam(booster.getId(), key, String.valueOf(value));
			manager.reloadBooster(booster);
			player.sendMessage(MessageUtils.color("&aObject modified: &6" + key + ": &e" + String.valueOf(value)));
			player.openInventory(holder.getInventory());
		};
	}

	private ItemStack getBooleanItem(ItemStack item, boolean value) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessageUtils.color("&6&lModify -> &e&l" + String.valueOf(value)));
		item.setItemMeta(meta);
		return item;
	}

}
