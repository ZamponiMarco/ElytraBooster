package com.github.zamponimarco.elytrabooster.boosters;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;

public interface Booster {

	public BoosterManager<?> getDataManager();
	
	public String getId();
	
	public Location getCenter();
		
	public void stopBoosterTask();

	public void runBoosterTask();
	
}
