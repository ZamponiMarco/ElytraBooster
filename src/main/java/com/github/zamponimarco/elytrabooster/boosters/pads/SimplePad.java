package com.github.zamponimarco.elytrabooster.boosters.pads;

import org.bukkit.Location;
import org.bukkit.Particle;

import com.github.zamponimarco.elytrabooster.boosts.VerticalBoost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class SimplePad extends AbstractPad {

	public SimplePad(ElytraBooster plugin, String id, Location center, VerticalBoost boost) {
		super(plugin, id, center, boost);
		runBoosterTask();
	}

	public void runBoosterTask() {
		visualTaskId = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> visualEffect(), 0, 1).getTaskId();
	}

	private void visualEffect() {
		center.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, center, 10);
	}

	@Override
	public void stopBoosterTask() {
		plugin.getServer().getScheduler().cancelTask(visualTaskId);
	}

	@Override
	public Location getCenter() {
		return center;
	}

}
