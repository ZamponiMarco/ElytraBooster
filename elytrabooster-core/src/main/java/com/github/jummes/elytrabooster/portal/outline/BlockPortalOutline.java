package com.github.jummes.elytrabooster.portal.outline;

import com.github.jummes.libs.annotation.Serializable;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

@Getter
@SerializableAs("BlockPortalOutline")
public class BlockPortalOutline extends Outline {

    private static final String OUTLINE_TYPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQ1YzlhY2VhOGRhNzFiNGYyNTJjZDRkZWI1OTQzZjQ5ZTdkYmMwNzY0Mjc0YjI1YTZhNmY1ODc1YmFlYTMifX19";
    private static final String COOLDOWN_TYPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmZkYzRjODg5NWUzZjZiM2FjNmE5YjFjZDU1ZGUzYTI5YmJjOGM3ODVlN2ZiZGJkOTMyMmQ4YzIyMzEifX19";

    @Serializable(headTexture = OUTLINE_TYPE_HEAD, stringValue = true)
    private Material outlineType;
    @Serializable(headTexture = COOLDOWN_TYPE_HEAD, stringValue = true)
    private Material cooldownType;

    public BlockPortalOutline() {
        this(Material.STONE, Material.DIRT);
    }

    public BlockPortalOutline(Material outlineType, Material cooldownType) {
            this.outlineType = outlineType;
            this.cooldownType = cooldownType;
    }

    public static BlockPortalOutline deserialize(Map<String, Object> map) {
        Material outlineType = Material.valueOf((String) map.get("outlineType"));
        Material cooldownType = Material.valueOf((String) map.get("cooldownType"));
        return new BlockPortalOutline(outlineType, cooldownType);
    }

    @Override
    public void drawOutline(List<Location> points) {
        drawOutline(points, outlineType, false);
    }

    private void drawOutline(List<Location> points, Material m, boolean force) {

        Predicate<? super Location> predicate = force
                ? point -> point.getBlock().getType().equals(outlineType)
                || point.getBlock().getType().equals(cooldownType)
                : point -> point.getBlock().getType().equals(Material.AIR)
                || point.getBlock().getType().equals(cooldownType);

        points.stream().filter(predicate).forEach(point -> point.getBlock().setType(m));
    }

    @Override
    public void eraseOutline(List<Location> points) {
        drawOutline(points, Material.AIR, true);
    }

    @Override
    public void cooldownOutline(List<Location> points, int cooldown, int progress) {
        int cooldownBlocks = (int) ((progress / (double) cooldown) * points.size());
        IntStream.range(0, cooldownBlocks).forEach(i -> {
            if (!points.get(i).getBlock().getType().equals(cooldownType)) {
                points.get(i).getBlock().setType(cooldownType);
            }
        });
        IntStream.range(cooldownBlocks, points.size()).forEach(i -> {
            if (!points.get(i).getBlock().getType().equals(outlineType)) {
                points.get(i).getBlock().setType(outlineType);
            }
        });
    }

}
