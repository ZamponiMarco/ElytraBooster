package com.github.jummes.elytrabooster.pad.visual;

import com.github.jummes.libs.core.Libs;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlamePadVisual extends PadVisual {

    private static final String ARROW_UP_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNjYmY5ODgzZGQzNTlmZGYyMzg1YzkwYTQ1OWQ3Mzc3NjUzODJlYzQxMTdiMDQ4OTVhYzRkYzRiNjBmYyJ9fX0===";

    private List<Location> circlePoints;
    private Item item;

    public FlamePadVisual(Location center) {
        super(center);
    }

    @Override
    public void spawnVisual() {
        Random r = new Random();
        circlePoints.forEach(point -> point.getWorld().spawnParticle(Particle.FLAME, point, 0,
                r.nextDouble() * 0.1 - 0.05, 0.08, r.nextDouble() * 0.1 - 0.05));
        center.getWorld().spawnParticle(Particle.FLAME, center.clone().add(new Vector(0, 2.2, 0)), 1, 0.1, 0.1, 0.1, 0.01);
        if (item.isDead()) {
            stopVisual();
            initializeVisual();
        }
    }

    @Override
    public void onBoost() {
        center.getWorld().spawnParticle(Particle.FLAME, center.clone().add(new Vector(0, 1, 0)), 300, 0.3, 1, 0.3, 0.1,
                null);
        center.getWorld().spawnParticle(Particle.LAVA, center.clone().add(new Vector(0, 1, 0)), 10, 0.3, 1, 0.3, 0.1,
                null);
    }

    @Override
    public void initializeVisual() {
        circlePoints = new ArrayList<Location>();
        int amount = 20;
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
        item.setItemStack(Libs.getWrapper().skullFromValue(ARROW_UP_HEAD));
    }

    @Override
    public void stopVisual() {
        item.remove();
    }

    @Override
    public String getName() {
        return "flame";
    }

}
