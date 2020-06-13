package com.github.jummes.elytrabooster.boost.trail;

import org.bukkit.entity.Player;

import java.util.Map;

public class NoBoostTrail extends BoostTrail {

    public static NoBoostTrail deserialize(Map<String, Object> map) {
        return new NoBoostTrail();
    }

    @Override
    public void spawnTrail(Player player) {
    }

}
