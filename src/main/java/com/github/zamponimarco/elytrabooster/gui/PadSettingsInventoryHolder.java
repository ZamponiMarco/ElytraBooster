package com.github.zamponimarco.elytrabooster.gui;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.boosters.pads.AbstractPad;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.settings.DoubleSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.IntegerSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.SettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.enums.TrailEnumSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.enums.VisualEnumSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.utils.HeadsUtil;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;
import com.google.common.collect.Lists;

public class PadSettingsInventoryHolder extends ElytraBoosterInventoryHolder {

	private static final String HORIZONTAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ4NjVhYWUyNzQ2YTliOGU5YTRmZTYyOWZiMDhkMThkMGE5MjUxZTVjY2JlNWZhNzA1MWY1M2VhYjliOTQifX19=";
	private static final String VERTICAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTUxNDlkZGRhZGVkMjBkMjQ0ZTBiYjYyYTJkOWZhMGRjNmM2YTc4NjI1NTkzMjhhOTRmNzc3MjVmNTNjMzU4In19fQ=====";
	private static final String TRAIL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzYxOWJmNjI4NjNlYzExNTc3ZDZlZjY1ZWZkYzNmOWRlNGRmNDE0MjAyZWQxZmYxZGU5ZWM3NmI2MWEzZjY2NyJ9fX0========";
	private static final String VISUAL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmU2NTUxNmQ4MWFjOTYzZGJjMjQ4NTEzOGRkZGNmOTQzZDdmNzIxMWUzN2VmZWNkNWE1ZmI4ZjVhZDQ5MjAifX19========";
	private static final String COOLDOWN_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMyYzYxNzE1MzJhMmE4N2YwZWViMjhlZGQwMTA4MzNmMzNmMGFlNjg0MWE1MjRlMWI1MjAwYTM1ZDM4NTA1MCJ9fX0=";

	private AbstractPad pad;

	public PadSettingsInventoryHolder(ElytraBooster plugin, AbstractPad pad) {
		super(plugin);
		this.pad = pad;
		initializeInventory();
	}

	@Override
	protected void initializeInventory() {
		this.inventory = Bukkit.createInventory(this, 27,
				MessagesUtil.color(String.format("&6&lSpawner: &1&l%s", pad.getId())));
		registerClickConsumer(3,
				getPadSetting(HeadsUtil.skullFromValue(HORIZONTAL_VELOCITY_HEAD), "horizontalVelocity",
						pad.getBoost().getHorizontalVelocity()),
				getSettingConsumer("horizontalVelocity", pad.getBoost().getHorizontalVelocity(),
						DoubleSettingInventoryHolder.class));
		registerClickConsumer(5,
				getPadSetting(HeadsUtil.skullFromValue(VERTICAL_VELOCITY_HEAD), "verticalVelocity",
						pad.getBoost().getVerticalVelocity()),
				getSettingConsumer("verticalVelocity", pad.getBoost().getVerticalVelocity(),
						DoubleSettingInventoryHolder.class));
		registerClickConsumer(17,
				getPadSetting(HeadsUtil.skullFromValue(TRAIL_HEAD), "trail", pad.getBoost().getTrail().getName()),
				getSettingConsumer("trail", pad.getBoost().getTrail().getName(),
						TrailEnumSettingInventoryHolder.class));
		registerClickConsumer(26,
				getPadSetting(HeadsUtil.skullFromValue(VISUAL_HEAD), "visual", pad.getVisual().getName()),
				getSettingConsumer("visual", pad.getVisual().getName(), VisualEnumSettingInventoryHolder.class));
		registerClickConsumer(13, getPadSetting(HeadsUtil.skullFromValue(COOLDOWN_HEAD), "cooldown", pad.getCooldown()),
				getSettingConsumer("cooldown", pad.getCooldown(), IntegerSettingInventoryHolder.class));
		registerClickConsumer(18, getDeleteItem(), getDeleteConsumer(pad));
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private Consumer<InventoryClickEvent> getSettingConsumer(String key, Object value,
			Class<? extends SettingInventoryHolder> clazz) {
		return e -> {
			if (e.getClick().equals(ClickType.LEFT)) {
				HumanEntity p = e.getWhoClicked();
				try {
					p.closeInventory();
					p.openInventory(clazz.getConstructor(ElytraBooster.class, String.class, Booster.class,
							HumanEntity.class, Object.class).newInstance(plugin, key, pad, p, value).getInventory());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	private List<String> getDefaultLore() {
		return Lists.newArrayList(MessagesUtil.color("&6&l- &e&lLeft click &6to modify"));
	}

	private ItemStack getPadSetting(ItemStack item, String key, Object value) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessagesUtil.color(String.format("&6&l%s = &e&l%s", key, value.toString())));
		meta.setLore(getDefaultLore());
		item.setItemMeta(meta);
		return item;
	}

}
