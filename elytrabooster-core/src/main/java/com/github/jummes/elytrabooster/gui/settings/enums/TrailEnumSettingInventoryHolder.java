package com.github.jummes.elytrabooster.gui.settings.enums;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.managers.boosters.BoosterManager;

public class TrailEnumSettingInventoryHolder extends EnumSettingInventoryHolder {

	private static final String SIMPLE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAyZjQ4ZjM0ZDIyZGVkNzQwNGY3NmU4YTEzMmFmNWQ3OTE5YzhkY2Q1MWRmNmU3YTg1ZGRmYWM4NWFiIn19fQ==";
	private static final String RAINBOW_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY5ZTNlNmU1YjJiOTJmMGJlYjM2OGI3MzhiOTkzZDdiYTIyNWJmOWJiMjc1OGJmYzlmYzJkYWJhNGE1YTdkIn19fQ====";
	private static final String HELIX_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE3MDg1NzRlN2U5YTZhNzZjOWFhODIxNDc5N2IxYWMzODk1MDY4OWVmOWNkZGExOGQ1MDM2MjM5OGI2MTAxZCJ9fX0=";
	private static final String WINGS_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWRiZDhiNmNiM2IxZWNkOWNhMzU5YjgzM2MxYmVmMjJlOTczMmZiN2FhMGQwZmY0ZmI1NTg4ODZmNWE2YSJ9fX0==";
	private static final String NONE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ===";

	
	public TrailEnumSettingInventoryHolder(BoosterManager<?> manager, ConfigurationSection section, String key, Object value,
			HumanEntity player, InventoryHolder holder) {
		super(manager, section, key, value, player, holder);
	}

	@Override
	public void setUpMap() {
		headsMap.put("simple", ElytraBooster.getInstance().getWrapper().skullFromValue(SIMPLE_HEAD));
		headsMap.put("rainbow", ElytraBooster.getInstance().getWrapper().skullFromValue(RAINBOW_HEAD));
		headsMap.put("helix", ElytraBooster.getInstance().getWrapper().skullFromValue(HELIX_HEAD));
		headsMap.put("wings", ElytraBooster.getInstance().getWrapper().skullFromValue(WINGS_HEAD));
		headsMap.put("none", ElytraBooster.getInstance().getWrapper().skullFromValue(NONE_HEAD));
	}

}