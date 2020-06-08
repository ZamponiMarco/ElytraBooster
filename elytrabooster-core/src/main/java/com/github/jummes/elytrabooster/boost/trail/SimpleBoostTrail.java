package com.github.jummes.elytrabooster.boost.trail;

import com.github.jummes.libs.annotation.Serializable;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

import java.util.Map;

@SerializableAs("SimpleBoostTrail")
public class SimpleBoostTrail extends BoostTrail {

    private static final String PARTICLE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY4NDczNWZjOWM3NjBlOTVlYWYxMGNlYzRmMTBlZGI1ZjM4MjJhNWZmOTU1MWVlYjUwOTUxMzVkMWZmYTMwMiJ9fX0=";

    @Serializable(headTexture = PARTICLE_HEAD, stringValue = true)
    private Particle particle;

    public SimpleBoostTrail(){
        this(Particle.FIREWORKS_SPARK);
    }

    public SimpleBoostTrail(Particle particle) {
        this.particle = particle;
    }

    public static SimpleBoostTrail deserialize(Map<String, Object> map) {
        Particle particle = Particle.valueOf((String) map.get("particle"));
        return new SimpleBoostTrail(particle);
    }

    @Override
    public void spawnTrail(Player player) {
        player.getWorld().spawnParticle(particle, player.getLocation(), 3, 0.1, 0.1, 0.1, 0.1);
    }

}
