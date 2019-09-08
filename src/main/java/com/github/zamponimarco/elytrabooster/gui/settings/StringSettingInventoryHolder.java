package com.github.zamponimarco.elytrabooster.gui.settings;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;
import com.github.zamponimarco.elytrabooster.utils.HeadUtils;
import com.github.zamponimarco.elytrabooster.utils.MessageUtils;

public class StringSettingInventoryHolder extends SettingInventoryHolder implements Listener {

	private static final String MODIFY_MESSAGE = MessageUtils.color(
			"&aTo modify the parameter type in chat the &6&lnew value&a.\n&aType &6&l'exit' &ato leave the value unmodified.");
	private static final String MODIFY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE3NWM4ZTUxYzNkMTA1YmFiNGM3ZGUzM2E3NzA5MzczNjRiNWEwMWMxNWI3ZGI4MmNjM2UxZmU2ZWI5MzM5NiJ9fX0==";

	private static final String CREATE_MESSAGE = MessageUtils
			.color("&aTo create the booster type in chat his &6&lid&a.\n&aType &6&l'exit' &ato cancel creation.");

	private static Map<HumanEntity, Entry<BoosterManager<?>, Entry<ConfigurationSection, Entry<String, InventoryHolder>>>> settingsMap = new HashMap<>();

	private boolean isCreation;

	public StringSettingInventoryHolder(BoosterManager<?> manager, ConfigurationSection section, String key,
			Object value, HumanEntity player, InventoryHolder holder) {
		super(manager, section, key, value, player, holder);
		isCreation = key == null;
	}

	@Override
	protected void initializeInventory() {
		this.inventory = Bukkit.createInventory(this, 27,
				MessageUtils.color(isCreation ? "&6&lCreate a " + key : "&6&lModify &e&l" + key));
		registerClickConsumer(13, getStringItem(HeadUtils.skullFromValue(MODIFY_HEAD)), e -> playerCanWrite());
		registerClickConsumer(26, getBackItem(), getBackConsumer());
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private void playerCanWrite() {
		player.sendMessage(isCreation ? CREATE_MESSAGE : MODIFY_MESSAGE);
		settingsMap.put(player, new AbstractMap.SimpleEntry<>(manager,
				new AbstractMap.SimpleEntry<>(section, new AbstractMap.SimpleEntry<>(key, holder))));
		player.closeInventory();
	}

	public static Map<HumanEntity, Entry<BoosterManager<?>, Entry<ConfigurationSection, Entry<String, InventoryHolder>>>> getSettingsMap() {
		return settingsMap;
	}

	private ItemStack getStringItem(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessageUtils.color(isCreation ? "&6&lCreate " + key : "&6&lModify Value"));
		item.setItemMeta(meta);
		return item;
	}

}
