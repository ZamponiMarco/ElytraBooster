package com.github.zamponimarco.elytrabooster.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.boosters.pads.AbstractPad;
import com.github.zamponimarco.elytrabooster.gui.settings.DoubleSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.IntegerSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.enums.TrailEnumSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.enums.VisualEnumSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.managers.boosters.PadManager;
import com.github.zamponimarco.elytrabooster.utils.HeadUtils;
import com.github.zamponimarco.elytrabooster.utils.MessageUtils;
import com.google.common.collect.Lists;

public class PadSettingsInventoryHolder extends ElytraBoosterInventoryHolder {

	private static final String HORIZONTAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ4NjVhYWUyNzQ2YTliOGU5YTRmZTYyOWZiMDhkMThkMGE5MjUxZTVjY2JlNWZhNzA1MWY1M2VhYjliOTQifX19=";
	private static final String VERTICAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTUxNDlkZGRhZGVkMjBkMjQ0ZTBiYjYyYTJkOWZhMGRjNmM2YTc4NjI1NTkzMjhhOTRmNzc3MjVmNTNjMzU4In19fQ=====";
	private static final String TRAIL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzYxOWJmNjI4NjNlYzExNTc3ZDZlZjY1ZWZkYzNmOWRlNGRmNDE0MjAyZWQxZmYxZGU5ZWM3NmI2MWEzZjY2NyJ9fX0========";
	private static final String VISUAL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmU2NTUxNmQ4MWFjOTYzZGJjMjQ4NTEzOGRkZGNmOTQzZDdmNzIxMWUzN2VmZWNkNWE1ZmI4ZjVhZDQ5MjAifX19========";
	private static final String COOLDOWN_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMyYzYxNzE1MzJhMmE4N2YwZWViMjhlZGQwMTA4MzNmMzNmMGFlNjg0MWE1MjRlMWI1MjAwYTM1ZDM4NTA1MCJ9fX0=";

	private InventoryHolder holder;
	private String padId;

	public PadSettingsInventoryHolder(String padId, InventoryHolder holder) {
		this.padId = padId;
		this.holder = holder;
	}

	@Override
	protected void initializeInventory() {
		PadManager manager = plugin.getPadManager();

		ConfigurationSection section = manager.getDataYaml().getConfigurationSection(padId);

		AbstractPad pad = manager.getBooster(padId);

		this.inventory = Bukkit.createInventory(this, 27,
				MessageUtils.color(String.format("&6&lSpawner: &1&l%s", pad.getId())));
		registerClickConsumer(3,
				getPadSetting(HeadUtils.skullFromValue(HORIZONTAL_VELOCITY_HEAD), "horizontalVelocity",
						pad.getBoost().getHorizontalVelocity()),
				getSettingConsumer(manager, section, "horizontalVelocity", pad.getBoost().getHorizontalVelocity(),
						DoubleSettingInventoryHolder.class));
		registerClickConsumer(5,
				getPadSetting(HeadUtils.skullFromValue(VERTICAL_VELOCITY_HEAD), "verticalVelocity",
						pad.getBoost().getVerticalVelocity()),
				getSettingConsumer(manager, section, "verticalVelocity", pad.getBoost().getVerticalVelocity(),
						DoubleSettingInventoryHolder.class));
		registerClickConsumer(8,
				getPadSetting(HeadUtils.skullFromValue(TRAIL_HEAD), "trail", pad.getBoost().getTrail().getName()),
				getSettingConsumer(manager, section, "trail", pad.getBoost().getTrail().getName(),
						TrailEnumSettingInventoryHolder.class));
		registerClickConsumer(17,
				getPadSetting(HeadUtils.skullFromValue(VISUAL_HEAD), "visual", pad.getVisual().getName()),
				getSettingConsumer(manager, section, "visual", pad.getVisual().getName(),
						VisualEnumSettingInventoryHolder.class));
		registerClickConsumer(13, getPadSetting(HeadUtils.skullFromValue(COOLDOWN_HEAD), "cooldown", pad.getCooldown()),
				getSettingConsumer(manager, section, "cooldown", pad.getCooldown(),
						IntegerSettingInventoryHolder.class));
		registerClickConsumer(18, getDeleteItem(), getDeleteConsumer(manager, section));
		registerClickConsumer(26, getBackItem(), e -> e.getWhoClicked().openInventory(holder.getInventory()));
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private List<String> getDefaultLore() {
		return Lists.newArrayList(MessageUtils.color("&6&l- &e&lLeft click &6to modify"));
	}

	private ItemStack getPadSetting(ItemStack item, String key, Object value) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessageUtils.color(String.format("&6&l%s = &e&l%s", key, value.toString())));
		meta.setLore(getDefaultLore());
		item.setItemMeta(meta);
		return item;
	}

}
