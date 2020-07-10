package com.github.jummes.elytrabooster.spawner.entityholder.entity.description;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.core.Libs;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Map;

@Getter
@Setter
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lFirework Entity", description = "gui.spawner.entityHolder.entityDescription.firework.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAyZjQ4ZjM0ZDIyZGVkNzQwNGY3NmU4YTEzMmFmNWQ3OTE5YzhkY2Q1MWRmNmU3YTg1ZGRmYWM4NWFiIn19fQ==")
public class FireworkEntityDescription extends EntityDescription {

    private static final String FIREWORK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAyZjQ4ZjM0ZDIyZGVkNzQwNGY3NmU4YTEzMmFmNWQ3OTE5YzhkY2Q1MWRmNmU3YTg1ZGRmYWM4NWFiIn19fQ==";

    private Item item;
    private int effectTaskNumber;

    public FireworkEntityDescription() {
        super();
    }

    @SuppressWarnings("unused")
    public static FireworkEntityDescription deserialize(Map<String, Object> map) {
        return new FireworkEntityDescription();
    }

    @Override
    public void spawn(Location location) {
        item = location.getWorld().dropItem(location, Libs.getWrapper().skullFromValue(FIREWORK_HEAD));
        item.setGravity(false);
        item.setVelocity(new Vector());
        item.setPickupDelay(32767);
        item.setInvulnerable(true);
        effectTaskNumber = runEffectTask(location);
    }

    @Override
    public void entityDespawn() {
        plugin.getServer().getScheduler().cancelTask(effectTaskNumber);
        item.remove();
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
                    location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location, 3, 0.1, 0.1, 0.1, 0.01);
                }
            }
        };
        return runnable.runTaskTimer(plugin, 0, 4).getTaskId();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new FireworkEntityDescription();
    }

}
