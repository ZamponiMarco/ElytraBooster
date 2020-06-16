package com.github.jummes.elytrabooster.spawner.entityholder.entity;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.core.Libs;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Map;

@Enumerable.Child(name = "&c&lPotion Entity", description = "gui.spawner.entityHolder.entityDescription.potion.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTMxMDRmMTlhOTQ1YzYyZTEwMzJkZTZlNmM2MzQyMDY2NDdkOTRlZDljMGE1ODRlNmQ2YjZkM2E0NzVmNTIifX19==")
public class PotionEntityDescription extends EntityDescription {

    private static final String POTION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTMxMDRmMTlhOTQ1YzYyZTEwMzJkZTZlNmM2MzQyMDY2NDdkOTRlZDljMGE1ODRlNmQ2YjZkM2E0NzVmNTIifX19==";

    private Item item;
    private int effectTaskNumber;

    public PotionEntityDescription() {
        super();
    }

    @SuppressWarnings("unused")
    public static PotionEntityDescription deserialize(Map<String, Object> map) {
        return new PotionEntityDescription();
    }

    @Override
    public void spawn(Location location) {
        item = (Item) location.getWorld().spawnEntity(location, EntityType.DROPPED_ITEM);
        item.setGravity(false);
        item.setVelocity(new Vector());
        item.setPickupDelay(32767);
        item.setInvulnerable(true);
        item.setItemStack(Libs.getWrapper().skullFromValue(POTION_HEAD));
        effectTaskNumber = runEffectTask(location);
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
                    location.getWorld().spawnParticle(Particle.SPELL_MOB, location, 0, 0.85, 1, 1, 1);
                }
            }
        };
        return runnable.runTaskTimer(plugin, 0, 10).getTaskId();
    }

    @Override
    protected Object clone() {
        return new PotionEntityDescription();
    }
}
