package com.github.jummes.elytrabooster.gui.settings.enums;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.managers.boosters.BoosterManager;

public class AxisEnumSettingInventoryHolder extends EnumSettingInventoryHolder {

	public static final String X_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY3NGQ0ZGI0Y2MzYmU0MWEzNzNkOWVmOWNhYzI3ZTYzNThjNTNmNjQxMTVkMTUwMjQzZjI1YWNmNjRmMmY1MCJ9fX0=";
	public static final String Y_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzljMTBkODI4MzkyNmQ3MjBmZDNkZTE1YzRlNGNkM2UxNTlmYjI1NmY3ZmE4ZDg5N2ViMmYxNGFiOGExOCJ9fX0==";
	public static final String Z_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY0OTE3YzI0MTQ5NDFlZmY3ZTAxYmM5YmQxNTljNjk5ZTliZWUyZDg4ZTMxNWVhZDJiOWNlYzBjYmU1MWM5OCJ9fX0==";

	public AxisEnumSettingInventoryHolder(BoosterManager<?> manager, ConfigurationSection section, String key, Object value,
			HumanEntity player, InventoryHolder holder) {
		super(manager, section, key, value, player, holder);
	}

	@Override
	public void setUpMap() {
		headsMap.put("x", ElytraBooster.getInstance().getWrapper().skullFromValue(X_HEAD));
		headsMap.put("y", ElytraBooster.getInstance().getWrapper().skullFromValue(Y_HEAD));
		headsMap.put("z", ElytraBooster.getInstance().getWrapper().skullFromValue(Z_HEAD));
	}

}
