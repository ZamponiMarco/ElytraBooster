package com.github.jummes.elytrabooster.portal.outline;

import com.github.jummes.libs.annotation.Serializable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ParticlePortalOutline extends Outline {

    private static final String OUTLINE_TYPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQ1YzlhY2VhOGRhNzFiNGYyNTJjZDRkZWI1OTQzZjQ5ZTdkYmMwNzY0Mjc0YjI1YTZhNmY1ODc1YmFlYTMifX19";
    private static final String COOLDOWN_TYPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmZkYzRjODg5NWUzZjZiM2FjNmE5YjFjZDU1ZGUzYTI5YmJjOGM3ODVlN2ZiZGJkOTMyMmQ4YzIyMzEifX19";

    @Serializable(headTexture = OUTLINE_TYPE_HEAD, stringValue = true)
    private Particle outlineType;
    @Serializable(headTexture = COOLDOWN_TYPE_HEAD, stringValue = true)
    private Particle cooldownType;

    public ParticlePortalOutline() {
        this(Particle.FLAME, Particle.FIREWORKS_SPARK);
    }

    public ParticlePortalOutline(Particle outlineType, Particle cooldownType) {
        this.outlineType = outlineType;
        this.cooldownType = cooldownType;
    }

    public static ParticlePortalOutline deserialize(Map<String, Object> map) {
        Particle outlineType = Particle.valueOf((String) map.get("outlineType"));
        Particle cooldownType = Particle.valueOf((String) map.get("cooldownType"));
        return new ParticlePortalOutline(outlineType, cooldownType);
    }

    @Override
    public void drawOutline(List<Location> points) {
        points.forEach(point -> {
            point.getWorld().spawnParticle(outlineType, point, 1, 0, 0, 0, 0.0, null, true);
        });
    }

    @Override
    public void eraseOutline(List<Location> points) {
    }

    @Override
    public void cooldownOutline(List<Location> points, int cooldown, int progress) {
        int cooldownBlocks = (int) ((progress / (double) cooldown) * points.size());
        IntStream.range(0, cooldownBlocks).forEach(i -> {
            Location point = points.get(i);
            point.getWorld().spawnParticle(cooldownType, point, 1, 0, 0, 0, 0.0, null, true);
        });
        IntStream.range(cooldownBlocks, points.size()).forEach(i -> {
            Location point = points.get(i);
            point.getWorld().spawnParticle(outlineType, point, 1, 0, 0, 0, 0.0, null, true);
        });
    }

    @Override
    public Object getOutlineType() {
        return outlineType;
    }

    @Override
    public Object getCooldownType() {
        return cooldownType;
    }

}
