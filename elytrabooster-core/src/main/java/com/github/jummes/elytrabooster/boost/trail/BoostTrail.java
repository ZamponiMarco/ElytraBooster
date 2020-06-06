package com.github.jummes.elytrabooster.boost.trail;

import com.github.jummes.libs.model.Model;
import org.bukkit.entity.Player;

/**
 * Interface that manages the creation of boost trails
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
