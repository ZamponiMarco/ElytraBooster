package com.github.jummes.elytrabooster.boost.trail;

import com.github.jummes.libs.annotation.Serializable;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Map;

public class HelixBoostTrail extends BoostTrail {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD)
    private Particle particle;
    private int i = 0;

    public HelixBoostTrail() {
        this(Particle.FIREWORKS_SPARK);
    }

    public HelixBoostTrail(Particle particle) {
        this.particle = particle;
    }

    public static HelixBoostTrail deserialize(Map<String, Object> map) {
        Particle particle = (Particle) map.get("particle");
        return new HelixBoostTrail(particle);
    }

    @Override
    public void spawnTrail(Player player) {
        int amount = 10;
        double increment = (2 * Math.PI) / amount;
        i = (i + 1) % amount;
        Vector direction = player.getLocation().getDirection();
        Vector toRotate = new Vector(0.5, 0.5, 0.5);
        Location toSpawn1 = player.getLocation().clone().add(toRotate.rotateAroundAxis(direction, i * increment));
        player.getWorld().spawnParticle(particle, toSpawn1, 5, 0.1, 0.1, 0.1, 0.1);

    }

}
