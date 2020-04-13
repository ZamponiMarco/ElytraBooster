package com.github.jummes.elytrabooster.entity;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.github.jummes.libs.core.Libs;

public class MushroomEntityDescription extends EntityDescription {

    private static final String MUSHROOM_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzU3MTk1MmEzNWMzMTYzYjhjMzNhMDkxOGQ2ZTlhNDUzM2Y2MjA1M2FkNGU2Y2ZjYjFmYTI3ZjU1MWFlZjIifX19==";

    private Item item;
    private int effectTaskNumber;

    public MushroomEntityDescription() {
        super();
    }

    @Override
    public int spawn(Location location) {
        item = (Item) location.getWorld().spawnEntity(location, EntityType.DROPPED_ITEM);
        item.setGravity(false);
        item.setVelocity(new Vector());
        item.setPickupDelay(32767);
        item.setInvulnerable(true);
        item.setItemStack(Libs.getWrapper().skullFromValue(MUSHROOM_HEAD));
        effectTaskNumber = runEffectTask(location);
        return effectTaskNumber;
    }

    @Override
    public void entityDespawn() {
        item.remove();
        plugin.getServer().getScheduler().cancelTask(effectTaskNumber);
    }

    private int runEffectTask(Location location) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (item.isDead()) {
                    entityDespawn();
                    spawn(location);
                    this.cancel();
                } else {
                    location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,
                            location.clone().add(new Vector(0, 0.15, 0)), 1, 0.15, 0.15, 0.15, 0.1);
                }
            }
        };
        return runnable.runTaskTimer(plugin, 0, 5).getTaskId();
    }

    @SuppressWarnings("unused")
    public static MushroomEntityDescription deserialize(Map<String, Object> map) {
        return new MushroomEntityDescription();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new MushroomEntityDescription();
    }
}
