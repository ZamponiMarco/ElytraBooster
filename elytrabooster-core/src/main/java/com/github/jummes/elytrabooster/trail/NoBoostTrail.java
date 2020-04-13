package com.github.jummes.elytrabooster.trail;

import org.bukkit.entity.Player;

public class NoBoostTrail extends BoostTrail {

    @Override
    public void spawnTrail(Player player) {
    }

    @Override
    public String getName() {
        return "none";
    }

}
