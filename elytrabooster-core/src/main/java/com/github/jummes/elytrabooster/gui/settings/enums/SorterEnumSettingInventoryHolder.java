package com.github.jummes.elytrabooster.gui.settings.enums;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.managers.boosters.BoosterManager;

public class SorterEnumSettingInventoryHolder extends EnumSettingInventoryHolder {

	private static final String NONE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==";
	private static final String CLOSING_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQ3NjUxYmM4NmI2YWJkODllZTdlYTY1NGQ0NjkwY2E2NDc0ZmFlMWY3ZjZkMjhiYzdkNGU0MTE2YTc0In19fQ==";
	private static final String RANDOM_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWMxZWE4MjA0Y2FiOGYzMzI3ZmZjZWY0OTJkMTkzZGE2MzQ0YThmODY0NTUyNDQyZTE1NWZlNzNiYjZiYSJ9fX0===";

	
	public SorterEnumSettingInventoryHolder(BoosterManager<?> manager, ConfigurationSection section, String key, Object value,
			HumanEntity player, InventoryHolder holder) {
		super(manager, section, key, value, player, holder);
	}

	@Override
	public void setUpMap() {
		headsMap.put("none", ElytraBooster.getInstance().getWrapper().skullFromValue(NONE_HEAD));
		headsMap.put("closing", ElytraBooster.getInstance().getWrapper().skullFromValue(CLOSING_HEAD));
		headsMap.put("random", ElytraBooster.getInstance().getWrapper().skullFromValue(RANDOM_HEAD));
	}

}
