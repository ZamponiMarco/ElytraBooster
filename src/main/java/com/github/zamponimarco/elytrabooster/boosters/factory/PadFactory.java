package com.github.zamponimarco.elytrabooster.boosters.factory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import com.github.zamponimarco.elytrabooster.boosters.pads.AbstractPad;
import com.github.zamponimarco.elytrabooster.boosters.pads.SimplePad;
import com.github.zamponimarco.elytrabooster.boosters.pads.visuals.PadVisual;
import com.github.zamponimarco.elytrabooster.boosters.pads.visuals.factory.PadVisualFactory;
import com.github.zamponimarco.elytrabooster.boosts.VerticalBoost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.trails.BoostTrail;
import com.github.zamponimarco.elytrabooster.trails.factory.BoostTrailFactory;

public class PadFactory implements BoosterFactory {
	public static AbstractPad buildBooster(ElytraBooster plugin, ConfigurationSection padConfiguration) {
		String id = padConfiguration.getName();

		World world = plugin.getServer().getWorld(padConfiguration.getString("world"));
		double x = padConfiguration.getDouble("x");
		double y = padConfiguration.getDouble("y");
		double z = padConfiguration.getDouble("z");
		Location center = new Location(world, x, y, z);

		double verticalVelocity = padConfiguration.getDouble("verticalVelocity", 2.0);

		double horizontalVelocity = padConfiguration.getDouble("horizontalVelocity", 3.0);

		List<String> boostActions = new ArrayList<String>();
		boostActions = padConfiguration.getStringList("boostActions");

		String trailString = padConfiguration.getString("trail", "simple");
		BoostTrail trail = BoostTrailFactory.buildBoostTrail(trailString);

		VerticalBoost boost = new VerticalBoost(trail, boostActions, verticalVelocity, horizontalVelocity);

		String visualString = padConfiguration.getString("visual", "flame");
		PadVisual visual = PadVisualFactory.buildPadVisual(visualString, center);

		return new SimplePad(plugin, id, center, boost, visual);
	}
}
