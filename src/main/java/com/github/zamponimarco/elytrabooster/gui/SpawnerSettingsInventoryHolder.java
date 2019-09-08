package com.github.zamponimarco.elytrabooster.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.boosters.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.gui.settings.DoubleSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.IntegerSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.enums.EntityEnumSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.enums.TrailEnumSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.managers.boosters.SpawnerManager;
import com.github.zamponimarco.elytrabooster.utils.HeadUtils;
import com.github.zamponimarco.elytrabooster.utils.MessageUtils;
import com.google.common.collect.Lists;

public class SpawnerSettingsInventoryHolder extends ElytraBoosterInventoryHolder {

	private static final String INITIAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJjZGM2Zjg4Yzg1M2U4MzE0OTVhMTc0NmViMjdhYTYxYjlkYWMyZTg2YTQ0Yjk1MjJlM2UyYjdkYzUifX19=";
	private static final String FINAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI5MjBjMzgxNWI5YzQ1OTJlNjQwOGUzMjIzZjMxMzUxZmM1NzhmMzU1OTFiYzdmOWJlYmQyMWVmZDhhMDk3In19fQ===";
	private static final String BOOST_DURATION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Q1MWM4M2NjMWViY2E1YTFiNmU2Nzk0N2UyMGI0YTJhNmM5ZWZlYTBjZjQ2OTI5NDQ4ZTBlMzc0MTZkNTgzMyJ9fX0====";
	private static final String COOLDOWN_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMyYzYxNzE1MzJhMmE4N2YwZWViMjhlZGQwMTA4MzNmMzNmMGFlNjg0MWE1MjRlMWI1MjAwYTM1ZDM4NTA1MCJ9fX0=";
	private static final String TRAIL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzYxOWJmNjI4NjNlYzExNTc3ZDZlZjY1ZWZkYzNmOWRlNGRmNDE0MjAyZWQxZmYxZGU5ZWM3NmI2MWEzZjY2NyJ9fX0========";
	private static final String MIN_RADIUS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M4ZTdkNDZkNjkzMzQxZjkxZDI4NjcyNmYyNTU1ZWYxNTUxNGUzNDYwYjI3NWU5NzQ3ODQyYmM5ZTUzZGYifX19========";
	private static final String MAX_RADIUS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTVhNzQwMzJhMjlmNzk0ZmQ1NjY0Yzg2N2VjYjQ0ZGE4MjE1ZDE2MGJmYzgwZDJiOTMzZTRiNTNjMWU5OWNhIn19fQ==========";
	private static final String MAX_ENTITIES_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTIxZDJjY2U5NTViZmZjZGE0Y2MwMzY3Yzg4NjQ0NDg4YjU5NWYyN2ZjZTE2N2I0MzRjYTViOGNkNDQ4ZCJ9fX0===========";
	private static final String ENTITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAyZjQ4ZjM0ZDIyZGVkNzQwNGY3NmU4YTEzMmFmNWQ3OTE5YzhkY2Q1MWRmNmU3YTg1ZGRmYWM4NWFiIn19fQ=============";

	private InventoryHolder holder;
	private String spawnerId;

	public SpawnerSettingsInventoryHolder(String spawnerId, InventoryHolder holder) {
		this.spawnerId = spawnerId;
		this.holder = holder;
	}

	@Override
	protected void initializeInventory() {
		SpawnerManager manager = plugin.getSpawnerManager();

		ConfigurationSection section = manager.getDataYaml().getConfigurationSection(spawnerId);

		AbstractSpawner spawner = manager.getBooster(spawnerId);

		this.inventory = Bukkit.createInventory(this, 27,
				MessageUtils.color(String.format("&6&lSpawner: &1&l%s", spawner.getId())));
		registerClickConsumer(2,
				getSpawnerSetting(HeadUtils.skullFromValue(MIN_RADIUS_HEAD), "minRadius", spawner.getMinRadius()),
				getSettingConsumer(manager, section, "minRadius", spawner.getMinRadius(),
						DoubleSettingInventoryHolder.class));
		registerClickConsumer(3,
				getSpawnerSetting(HeadUtils.skullFromValue(MAX_RADIUS_HEAD), "maxRadius", spawner.getMaxRadius()),
				getSettingConsumer(manager, section, "maxRadius", spawner.getMaxRadius(),
						DoubleSettingInventoryHolder.class));
		registerClickConsumer(5,
				getSpawnerSetting(HeadUtils.skullFromValue(COOLDOWN_HEAD), "cooldown", spawner.getCooldown()),
				getSettingConsumer(manager, section, "cooldown", spawner.getCooldown(),
						IntegerSettingInventoryHolder.class));
		registerClickConsumer(6,
				getSpawnerSetting(HeadUtils.skullFromValue(MAX_ENTITIES_HEAD), "maxEntities",
						spawner.getHolder().getMaxEntities()),
				getSettingConsumer(manager, section, "maxEntities", spawner.getHolder().getMaxEntities(),
						IntegerSettingInventoryHolder.class));
		registerClickConsumer(12,
				getSpawnerSetting(HeadUtils.skullFromValue(INITIAL_VELOCITY_HEAD), "initialVelocity",
						spawner.getHolder().getBoost().getInitialVelocity()),
				getSettingConsumer(manager, section, "initialVelocity",
						spawner.getHolder().getBoost().getInitialVelocity(), DoubleSettingInventoryHolder.class));
		registerClickConsumer(13,
				getSpawnerSetting(HeadUtils.skullFromValue(FINAL_VELOCITY_HEAD), "finalVelocity",
						spawner.getHolder().getBoost().getFinalVelocity()),
				getSettingConsumer(manager, section, "finalVelocity", spawner.getHolder().getBoost().getFinalVelocity(),
						DoubleSettingInventoryHolder.class));
		registerClickConsumer(14,
				getSpawnerSetting(HeadUtils.skullFromValue(BOOST_DURATION_HEAD), "boostDuration",
						spawner.getHolder().getBoost().getBoostDuration()),
				getSettingConsumer(manager, section, "boostDuration", spawner.getHolder().getBoost().getBoostDuration(),
						IntegerSettingInventoryHolder.class));
		registerClickConsumer(8,
				getSpawnerSetting(HeadUtils.skullFromValue(ENTITY_HEAD), "entity", spawner.getHolder().getEntity()),
				getSettingConsumer(manager, section, "entity", spawner.getHolder().getEntity(),
						EntityEnumSettingInventoryHolder.class));
		registerClickConsumer(17,
				getSpawnerSetting(HeadUtils.skullFromValue(TRAIL_HEAD), "trail",
						spawner.getHolder().getBoost().getTrail().getName()),
				getSettingConsumer(manager, section, "trail", spawner.getHolder().getBoost().getTrail().getName(),
						TrailEnumSettingInventoryHolder.class));
		registerClickConsumer(18, getDeleteItem(), getDeleteConsumer(manager, section));
		registerClickConsumer(26, getBackItem(), e -> e.getWhoClicked().openInventory(holder.getInventory()));
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private List<String> getDefaultLore() {
		return Lists.newArrayList(MessageUtils.color("&6&l- &e&lLeft click &6to modify"));
	}

	private ItemStack getSpawnerSetting(ItemStack item, String key, Object value) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessageUtils.color(String.format("&6&l%s = &e&l%s", key, value.toString())));
		meta.setLore(getDefaultLore());
		item.setItemMeta(meta);
		return item;
	}

}
