package com.github.jummes.elytrabooster.gui.settings.enums;

import java.util.LinkedHashMap;
import java.util.Map;
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
import com.github.jummes.elytrabooster.gui.settings.SettingInventoryHolder;
import com.github.jummes.elytrabooster.gui.settings.StringSettingInventoryHolder;
import com.github.jummes.elytrabooster.managers.boosters.BoosterManager;
import com.github.jummes.elytrabooster.utils.MessageUtils;

public abstract class EnumSettingInventoryHolder extends SettingInventoryHolder {

	private static final String PENCIL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjI4ZDk4Y2U0N2ZiNzdmOGI2MDRhNzY2ZGRkMjU0OTIzMjU2NGY5NTYyMjVjNTlmM2UzYjdiODczYTU4YzQifX19==";

	Map<String, ItemStack> headsMap;

	public EnumSettingInventoryHolder(BoosterManager<?> manager, ConfigurationSection section, String key, Object value,
			HumanEntity player, InventoryHolder holder) {
		super(manager, section, key, value, player, holder);
		headsMap = new LinkedHashMap<String, ItemStack>();
		setUpMap();
	}

	@Override
	protected void initializeInventory() {
		inventory = Bukkit.createInventory(this, 27, MessageUtils.color("&6&lModify &e&l" + key));
		int slot = 0;
		for (String value : headsMap.keySet()) {
			registerClickConsumer(slot, getEnumItem(value), getEnumConsumer(value));
			slot++;
		}
		registerClickConsumer(25, getStringItem(), e -> e.getWhoClicked()
				.openInventory(new StringSettingInventoryHolder(manager, section, key, value, player, holder).getInventory()));
		registerClickConsumer(26, getBackItem(), getBackConsumer());
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	public abstract void setUpMap();

	private Consumer<InventoryClickEvent> getEnumConsumer(String value) {
		return e -> {
			Booster booster = manager.getBooster(section.getName());
			manager.setParam(booster.getId(), key, String.valueOf(value));
			manager.reloadBooster(booster);
			player.sendMessage(MessageUtils.color("&aObject modified: &6" + key + ": &e" + value));
			player.openInventory(holder.getInventory());
		};
	}

	private ItemStack getEnumItem(String newValue) {
		ItemStack item = headsMap.get(newValue);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessageUtils.color("&6&lModify -> &e&l" + newValue));
		item.setItemMeta(meta);
		return item;
	}

	private ItemStack getStringItem() {
		ItemStack item = ElytraBooster.getInstance().getWrapper().skullFromValue(PENCIL_HEAD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessageUtils.color("&6&lSet a custom value"));
		item.setItemMeta(meta);
		return item;
	}

}
