package com.github.jummes.elytrabooster.gui.settings.enums;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.managers.boosters.BoosterManager;

public class ShapeEnumSettingInventoryHolder extends EnumSettingInventoryHolder {

	private static final String CIRCLE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQ3NjUxYmM4NmI2YWJkODllZTdlYTY1NGQ0NjkwY2E2NDc0ZmFlMWY3ZjZkMjhiYzdkNGU0MTE2YTc0In19fQ==";
	private static final String TRIANGLE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTRhOWZiMzU1MmQ1NTE1NTNkOWRkNDNjMmJiMWQyNjg4OTNkZjY4ZDczZTQ2MTEzNDNiNTcyYWU2NDI1Y2EifX19==";
	private static final String RECTANGLE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWMxZWE4MjA0Y2FiOGYzMzI3ZmZjZWY0OTJkMTkzZGE2MzQ0YThmODY0NTUyNDQyZTE1NWZlNzNiYjZiYSJ9fX0===";
	private static final String SQUARE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjRlYjgyNDVlOTJlYjY4YmI3N2VhMjZkNTU5YzM4YTNhZGYxOGYzY2VhNWJmMWRkZWM3ZDdmOGM1NTQ0NDhiIn19fQ====";
	
	public ShapeEnumSettingInventoryHolder(BoosterManager<?> manager, ConfigurationSection section, String key, Object value,
			HumanEntity player, InventoryHolder holder) {
		super(manager, section, key, value, player, holder);
	}

	@Override
	public void setUpMap() {
		headsMap.put("circle", ElytraBooster.getInstance().getWrapper().skullFromValue(CIRCLE_HEAD));
		headsMap.put("square", ElytraBooster.getInstance().getWrapper().skullFromValue(SQUARE_HEAD));
		headsMap.put("rectangle", ElytraBooster.getInstance().getWrapper().skullFromValue(RECTANGLE_HEAD));
		headsMap.put("triangle", ElytraBooster.getInstance().getWrapper().skullFromValue(TRIANGLE_HEAD));
	}

}
