package com.github.jummes.elytrabooster.trails;

import org.bukkit.entity.Player;

/**
 * Interface that manages the creatin of boost trails
 * 
 * @author Marco
 *
 */
public interface BoostTrail {

	/**
	 * Spawns a trail behind a player
	 * 
	 * @param player to spawn trail on
	 */
	public void spawnTrail(Player player);
	
	/**
	 * Gets the name of the trail
	 * 
	 * @return name of the trail
	 */
	public String getName();
	
}
