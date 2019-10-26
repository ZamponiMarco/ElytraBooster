package com.github.jummes.elytrabooster.boosters;

import org.bukkit.Location;

import com.github.jummes.elytrabooster.managers.boosters.BoosterManager;

public interface Booster {

	public BoosterManager<?> getDataManager();
	
	public String getId();
	
	public Location getCenter();
		
	public void stopBoosterTask();

	public void runBoosterTask();
	
}
