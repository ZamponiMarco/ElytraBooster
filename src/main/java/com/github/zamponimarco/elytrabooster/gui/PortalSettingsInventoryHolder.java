package com.github.zamponimarco.elytrabooster.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.boosters.portals.AbstractPortal;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.settings.BooleanSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.DoubleSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.IntegerSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.StringSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.enums.AxisEnumSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.enums.ShapeEnumSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.enums.SorterEnumSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.enums.TrailEnumSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.managers.boosters.PortalManager;
import com.github.zamponimarco.elytrabooster.outlines.ParticlePortalOutline;
import com.github.zamponimarco.elytrabooster.utils.HeadUtils;
import com.github.zamponimarco.elytrabooster.utils.MessageUtils;
import com.google.common.collect.Lists;

public class PortalSettingsInventoryHolder extends ElytraBoosterInventoryHolder {

	private static final String IS_BLOCK_OUTLINE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmEzYmI5M2I5OTMzNjg5YmM1MDg4ZGVjNzMwYmJlODU5ZDgyNmI2ZGFkNWZmZDc3M2MyZDJiOGY4NDdmNWYifX19";
	private static final String OUTLINE_TYPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQ1YzlhY2VhOGRhNzFiNGYyNTJjZDRkZWI1OTQzZjQ5ZTdkYmMwNzY0Mjc0YjI1YTZhNmY1ODc1YmFlYTMifX19";
	private static final String COOLDOWN_TYPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmZkYzRjODg5NWUzZjZiM2FjNmE5YjFjZDU1ZGUzYTI5YmJjOGM3ODVlN2ZiZGJkOTMyMmQ4YzIyMzEifX19";
	private static final String COOLDOWN_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMyYzYxNzE1MzJhMmE4N2YwZWViMjhlZGQwMTA4MzNmMzNmMGFlNjg0MWE1MjRlMWI1MjAwYTM1ZDM4NTA1MCJ9fX0=";
	private static final String INITIAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJjZGM2Zjg4Yzg1M2U4MzE0OTVhMTc0NmViMjdhYTYxYjlkYWMyZTg2YTQ0Yjk1MjJlM2UyYjdkYzUifX19=";
	private static final String FINAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI5MjBjMzgxNWI5YzQ1OTJlNjQwOGUzMjIzZjMxMzUxZmM1NzhmMzU1OTFiYzdmOWJlYmQyMWVmZDhhMDk3In19fQ===";
	private static final String BOOST_DURATION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Q1MWM4M2NjMWViY2E1YTFiNmU2Nzk0N2UyMGI0YTJhNmM5ZWZlYTBjZjQ2OTI5NDQ4ZTBlMzc0MTZkNTgzMyJ9fX0====";
	private static final String AXIS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTUxNDlkZGRhZGVkMjBkMjQ0ZTBiYjYyYTJkOWZhMGRjNmM2YTc4NjI1NTkzMjhhOTRmNzc3MjVmNTNjMzU4In19fQ======";
	private static final String SHAPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjBhMjg0ZTQzMGYwY2E0NTY1ZWZmNTIzMzJlZmUwMThjM2RlODlkZWVjNjhiZDk2NThkMTJiMmQ3YTQxZGIifX19======";
	private static final String MEASURES_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmM4ZGVmNjdhMTI2MjJlYWQxZGVjZDNkODkzNjQyNTdiNTMxODk2ZDg3ZTQ2OTgxMzEzMWNhMjM1YjVjNyJ9fX0=======";
	private static final String TRAIL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzYxOWJmNjI4NjNlYzExNTc3ZDZlZjY1ZWZkYzNmOWRlNGRmNDE0MjAyZWQxZmYxZGU5ZWM3NmI2MWEzZjY2NyJ9fX0========";
	private static final String SORTER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzIxNmQxN2RlNDJiZDA5NzY2OWI4ZTA5ZThlNjJkZjhiZjc4MzdkMzk1OTc1NDk2ZTYzNmZkYTRmYTk1ZjNkIn19fQ==========";

	private InventoryHolder holder;
	private String portalId;

	public PortalSettingsInventoryHolder(String portalId, InventoryHolder holder) {
		this.portalId = portalId;
		this.holder = holder;
	}

	@Override
	protected void initializeInventory() {
		PortalManager manager = ElytraBooster.getInstance().getPortalManager();

		ConfigurationSection section = manager.getDataYaml().getConfigurationSection(portalId);

		AbstractPortal portal = manager.getBooster(portalId);

		this.inventory = Bukkit.createInventory(this, 27,
				MessageUtils.color(String.format("&6&lPortal: &1&l%s", portal.getId())));
		registerClickConsumer(2,
				getPortalSetting(HeadUtils.skullFromValue(IS_BLOCK_OUTLINE_HEAD), "isBlockOutline",
						!(portal.getOutline() instanceof ParticlePortalOutline)),
				getSettingConsumer(manager, section, "isBlockOutline",
						!(portal.getOutline() instanceof ParticlePortalOutline), BooleanSettingInventoryHolder.class));
		registerClickConsumer(3,
				getPortalSetting(HeadUtils.skullFromValue(OUTLINE_TYPE_HEAD), "outlineType",
						portal.getOutline().getOutlineType()),
				getSettingConsumer(manager, section, "outlineType", portal.getOutline().getOutlineType(),
						StringSettingInventoryHolder.class));
		registerClickConsumer(5,
				getPortalSetting(HeadUtils.skullFromValue(COOLDOWN_TYPE_HEAD), "cooldownType",
						portal.getOutline().getCooldownType()),
				getSettingConsumer(manager, section, "cooldownType", portal.getOutline().getCooldownType(),
						StringSettingInventoryHolder.class));
		registerClickConsumer(6,
				getPortalSetting(HeadUtils.skullFromValue(COOLDOWN_HEAD), "cooldown", portal.getCooldown()),
				getSettingConsumer(manager, section, "cooldown", portal.getCooldown(),
						IntegerSettingInventoryHolder.class));
		registerClickConsumer(12,
				getPortalSetting(HeadUtils.skullFromValue(INITIAL_VELOCITY_HEAD), "initialVelocity",
						portal.getBoost().getInitialVelocity()),
				getSettingConsumer(manager, section, "initialVelocity", portal.getBoost().getInitialVelocity(),
						DoubleSettingInventoryHolder.class));
		registerClickConsumer(13,
				getPortalSetting(HeadUtils.skullFromValue(FINAL_VELOCITY_HEAD), "finalVelocity",
						portal.getBoost().getFinalVelocity()),
				getSettingConsumer(manager, section, "finalVelocity", portal.getBoost().getFinalVelocity(),
						DoubleSettingInventoryHolder.class));
		registerClickConsumer(14,
				getPortalSetting(HeadUtils.skullFromValue(BOOST_DURATION_HEAD), "boostDuration",
						portal.getBoost().getBoostDuration()),
				getSettingConsumer(manager, section, "boostDuration", portal.getBoost().getBoostDuration(),
						IntegerSettingInventoryHolder.class));
		registerClickConsumer(21, getPortalSetting(HeadUtils.skullFromValue(AXIS_HEAD), "axis", portal.getAxis()),
				getSettingConsumer(manager, section, "axis", portal.getAxis(), AxisEnumSettingInventoryHolder.class));
		registerClickConsumer(22, getPortalSetting(HeadUtils.skullFromValue(SHAPE_HEAD), "shape", portal.getShape()),
				getSettingConsumer(manager, section, "shape", portal.getShape(),
						ShapeEnumSettingInventoryHolder.class));
		registerClickConsumer(23,
				getPortalSetting(HeadUtils.skullFromValue(MEASURES_HEAD), "measures", portal.getMeasures()),
				getSettingConsumer(manager, section, "measures", portal.getMeasures(),
						StringSettingInventoryHolder.class));
		registerClickConsumer(8,
				getPortalSetting(HeadUtils.skullFromValue(TRAIL_HEAD), "trail", portal.getBoost().getTrail().getName()),
				getSettingConsumer(manager, section, "trail", portal.getBoost().getTrail().getName(),
						TrailEnumSettingInventoryHolder.class));
		registerClickConsumer(17,
				getPortalSetting(HeadUtils.skullFromValue(SORTER_HEAD), "sorter", portal.getSorter().getName()),
				getSettingConsumer(manager, section, "sorter", portal.getSorter().getName(),
						SorterEnumSettingInventoryHolder.class));
		registerClickConsumer(18, getDeleteItem(), getDeleteConsumer(manager, section));
		registerClickConsumer(26, getBackItem(), e -> e.getWhoClicked().openInventory(holder.getInventory()));
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private ItemStack getPortalSetting(ItemStack item, String key, Object value) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessageUtils.color(String.format("&6&l%s = &e&l%s", key, value.toString())));
		meta.setLore(getDefaultLore());
		item.setItemMeta(meta);
		return item;
	}

	private List<String> getDefaultLore() {
		return Lists.newArrayList(MessageUtils.color("&6&l- &e&lLeft click &6to modify"));
	}

}
