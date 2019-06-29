package com.github.zamponimarco.elytrabooster.boosters.pads;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.boosters.pads.visuals.PadVisual;
import com.github.zamponimarco.elytrabooster.boosts.VerticalBoost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;

public abstract class AbstractPad implements Booster {

	protected ElytraBooster plugin;
	protected String id;
	protected Location center;
	protected VerticalBoost boost;
	protected PadVisual visual;

	protected int visualTaskId;

	public AbstractPad(ElytraBooster plugin, String id, Location center, VerticalBoost boost, PadVisual visual) {
		this.plugin = plugin;
		this.id = id;
		this.center = center;
		this.boost = boost;
		this.visual = visual;
	}

	public void runBoosterTask() {
		visualTaskId = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> visual.spawnVisual(), 0, 4)
				.getTaskId();
	}

	@Override
	public void stopBoosterTask() {
		plugin.getServer().getScheduler().cancelTask(visualTaskId);
		visual.stopVisual();
	}

	public BoosterManager<?> getDataManager() {
		return plugin.getPadManager();
	}

	@Override
	public String getId() {
		return id;
	}

	public VerticalBoost getBoost() {
		return boost;
	}

	public PadVisual getVisual() {
		return visual;
	}

	@Override
	public Location getCenter() {
		return center;
	}

}
