package com.github.zamponimarco.elytrabooster.boosters.pads;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.boosts.VerticalBoost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;

public abstract class AbstractPad implements Booster {

	protected ElytraBooster plugin;
	protected String id;
	protected Location center;
	protected VerticalBoost boost;
	
	protected int visualTaskId;

	public AbstractPad(ElytraBooster plugin, String id, Location center, VerticalBoost boost) {
		this.plugin = plugin;
		this.id = id;
		this.center = center;
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

}
