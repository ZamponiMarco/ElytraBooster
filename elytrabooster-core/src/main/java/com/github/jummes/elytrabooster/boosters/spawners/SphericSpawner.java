package com.github.jummes.elytrabooster.boosters.spawners;

import org.bukkit.Location;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.entityholders.EntityHolder;

public class SphericSpawner extends AbstractSpawner {

	public SphericSpawner(ElytraBooster plugin, String id, Location center, double minRadius, double maxRadius,
			int cooldown, EntityHolder holder) {
		super(plugin, id, center, minRadius, maxRadius, cooldown, holder);
		runBoosterTask();
	}

}
