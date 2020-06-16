package com.github.jummes.elytrabooster.pad.visual;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.core.Libs;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Enumerable.Child(name = "&c&lFirework", description = "gui.pad.visual.firework.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAyZjQ4ZjM0ZDIyZGVkNzQwNGY3NmU4YTEzMmFmNWQ3OTE5YzhkY2Q1MWRmNmU3YTg1ZGRmYWM4NWFiIn19fQ==")
public class FireworkPadVisual extends PadVisual {

    private static final String FIREWORK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAyZjQ4ZjM0ZDIyZGVkNzQwNGY3NmU4YTEzMmFmNWQ3OTE5YzhkY2Q1MWRmNmU3YTg1ZGRmYWM4NWFiIn19fQ==";
    protected int visualTaskId;
    private List<Location> circlePoints;
    private Item item;

    public FireworkPadVisual() {
        super();
    }

    public static FireworkPadVisual deserialize(Map<String, Object> map) {
        return new FireworkPadVisual();
    }

    @Override
    public void startVisual(Location center) {
        initializeVisual(center);
        visualTaskId = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> spawnParticles(center), 0, 4)
                .getTaskId();
    }

    public void spawnParticles(Location center) {
        if (item.isDead()) {
            stopVisual();
            initializeVisual(center);
        }
        Random r = new Random();
        center.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, center.clone().add(new Vector(0, 2.1, 0)), 1, 0.1, 0,
                0.1, 0.01);
        circlePoints.forEach(point -> point.getWorld().spawnParticle(Particle.END_ROD, point, 0,
                r.nextDouble() * 0.1 - 0.05, 0.12, r.nextDouble() * 0.1 - 0.05));
    }

    @Override
    public void stopVisual() {
        item.remove();
        plugin.getServer().getScheduler().cancelTask(visualTaskId);
    }

    @Override
    public void onBoost(Location playerLocation) {
        playerLocation.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, playerLocation.clone().add(new Vector(0, 1, 0)), 300, 0.3, 1,
                0.3, 0.1, null);
        playerLocation.getWorld().spawnParticle(Particle.END_ROD, playerLocation.clone().add(new Vector(0, 1, 0)), 10, 0.3, 1, 0.3, 0.1,
                null);
    }

    @Override
    public void initializeVisual(Location center) {
        circlePoints = new ArrayList<>();
        int amount = 5;
        double increment = (2 * Math.PI) / amount;

        for (int i = 0; i < amount; i++) {
            double angle = i * increment;

            double x = center.getX() + (1 * Math.cos(angle));
            double z = center.getZ() + (1 * Math.sin(angle));

            circlePoints.add(new Location(center.getWorld(), x, center.getY(), z));
        }

        item = (Item) center.getWorld().spawnEntity(center.clone().add(new Vector(0, 2, 0)), EntityType.DROPPED_ITEM);
        item.setGravity(false);
        item.setVelocity(new Vector());
        item.setPickupDelay(32767);
        item.setInvulnerable(true);
        item.setItemStack(Libs.getWrapper().skullFromValue(FIREWORK_HEAD));
    }

}
