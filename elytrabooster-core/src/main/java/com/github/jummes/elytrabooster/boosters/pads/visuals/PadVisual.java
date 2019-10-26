package com.github.jummes.elytrabooster.boosters.pads.visuals;

import org.bukkit.Location;

public abstract class PadVisual {
	
	protected Location center;
	
	public PadVisual(Location center) {
		this.center = center;
		initializeVisual();
	}
	
	public abstract void spawnVisual();
	
	public abstract void onBoost();
	
	public abstract void initializeVisual();
	
	public abstract void stopVisual();

	public abstract String getName();
	
}
