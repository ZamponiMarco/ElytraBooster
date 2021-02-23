package com.github.jummes.elytrabooster.spawner.entityholder.entity.description;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.core.Libs;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lPotion Entity", description = "gui.spawner.entityHolder.entityDescription.potion.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTMxMDRmMTlhOTQ1YzYyZTEwMzJkZTZlNmM2MzQyMDY2NDdkOTRlZDljMGE1ODRlNmQ2YjZkM2E0NzVmNTIifX19==")
public class PotionEntityDescription extends EntityDescription {

    private static final String POTION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTMxMDRmMTlhOTQ1YzYyZTEwMzJkZTZlNmM2MzQyMDY2NDdkOTRlZDljMGE1ODRlNmQ2YjZkM2E0NzVmNTIifX19==";

    public PotionEntityDescription() {
        super();
    }

    @SuppressWarnings("unused")
    public static PotionEntityDescription deserialize(Map<String, Object> map) {
        return new PotionEntityDescription();
    }

    @Override
    protected ItemStack getHead() {
        return Libs.getWrapper().skullFromValue(POTION_HEAD);
    }

    @Override
    protected int getTaskPeriod() {
        return 10;
    }

    @Override
    protected void spawnParticle(Location location) {
        location.getWorld().spawnParticle(Particle.SPELL_MOB, location, 0, 0.85, 1, 1, 1);
    }

    @Override
    public EntityDescription clone() {
        return new PotionEntityDescription();
    }
}
