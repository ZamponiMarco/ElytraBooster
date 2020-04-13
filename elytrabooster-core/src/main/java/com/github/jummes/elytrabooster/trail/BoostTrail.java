package com.github.jummes.elytrabooster.trail;

import org.bukkit.entity.Player;

import com.github.jummes.libs.model.Model;

/**
 * Interface that manages the creatin of boost trails
 *
 * @author Marco
 */
public abstract class BoostTrail implements Model {

    /**
     * Spawns a trail behind a player
     *
     * @param player to spawn trail on
     */
    public abstract void spawnTrail(Player player);

    /**
     * Gets the name of the trail
     *
     * @return name of the trail
     */
    public abstract String getName();

}
