package com.github.jummes.elytrabooster.spawner.entityholder.entity.description;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.Model;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@Getter
@Enumerable.Parent(classArray = {PredefinedEntityDescription.class, CustomEntityDescription.class})
public abstract class EntityDescription implements Model, Cloneable {
    protected ElytraBooster plugin;
    protected Item item;
    protected int effectTaskNumber;

    public EntityDescription() {
        this.plugin = ElytraBooster.getInstance();
    }

    public void spawn(Location location) {
        item = location.getWorld().dropItem(location, getHead());
        item.setGravity(false);
        item.setVelocity(new Vector());
        item.setPickupDelay(32767);
        item.setInvulnerable(true);
        effectTaskNumber = runEffectTask(location);
    }

    public void entityDespawn() {
        plugin.getServer().getScheduler().cancelTask(effectTaskNumber);
        item.remove();
    }

    @Override
    public abstract EntityDescription clone();

    private int runEffectTask(Location location) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (item.isDead()) {
                    entityDespawn();
                    spawn(location);
                    this.cancel();
                } else {
                    spawnParticle(location);
                }
            }
        };
        return runnable.runTaskTimer(plugin, 0, getTaskPeriod()).getTaskId();
    }

    protected abstract ItemStack getHead();

    protected abstract int getTaskPeriod();

    protected abstract void spawnParticle(Location location);

}
