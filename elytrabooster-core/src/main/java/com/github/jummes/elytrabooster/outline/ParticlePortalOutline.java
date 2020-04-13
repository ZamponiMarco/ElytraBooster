package com.github.jummes.elytrabooster.outline;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;

import com.github.jummes.libs.annotation.Serializable;

public class ParticlePortalOutline extends Outline {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD, stringValue = true)
    private Particle outlineType;
    @Serializable(headTexture = HEAD, stringValue = true)
    private Particle cooldownType;

    public ParticlePortalOutline() {
        this("FLAME", "FLAME");
    }

    public ParticlePortalOutline(String outlineType, String cooldownType) {
        try {
            this.outlineType = Particle.valueOf(outlineType);
            this.cooldownType = Particle.valueOf(cooldownType);
        } catch (Exception e) {
            this.outlineType = Particle.FLAME;
            this.cooldownType = Particle.FLAME;
            Bukkit.getLogger().warning(
                    ChatColor.RED + outlineType + " or " + cooldownType + " is not a particle, check portals.yml");
        }
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

    public static ParticlePortalOutline deserialize(Map<String, Object> map) {
        String outlineType = (String) map.get("outlineType");
        String cooldownType = (String) map.get("cooldownType");
        return new ParticlePortalOutline(outlineType, cooldownType);
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
