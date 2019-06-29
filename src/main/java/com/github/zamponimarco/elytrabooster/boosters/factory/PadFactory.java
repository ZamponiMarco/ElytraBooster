package com.github.zamponimarco.elytrabooster.boosters.factory;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import com.github.zamponimarco.elytrabooster.boosters.pads.AbstractPad;
import com.github.zamponimarco.elytrabooster.boosters.pads.SimplePad;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class PadFactory implements BoosterFactory {
	public static AbstractPad buildBooster(ElytraBooster plugin, ConfigurationSection padConfiguration) {
		String id = padConfiguration.getName();

		World world = plugin.getServer().getWorld(padConfiguration.getString("world"));
		double x = padConfiguration.getDouble("x");
		double y = padConfiguration.getDouble("y");
		double z = padConfiguration.getDouble("z");
		Location center = new Location(world, x, y, z);
		
		return new SimplePad(plugin, id, center, null);
	}
}
