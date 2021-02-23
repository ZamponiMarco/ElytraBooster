package com.github.jummes.elytrabooster.spawner.entityholder.entity.description;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.core.Libs;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Getter
@Setter
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lFirework Entity", description = "gui.spawner.entityHolder.entityDescription.firework.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAyZjQ4ZjM0ZDIyZGVkNzQwNGY3NmU4YTEzMmFmNWQ3OTE5YzhkY2Q1MWRmNmU3YTg1ZGRmYWM4NWFiIn19fQ==")
public class FireworkEntityDescription extends EntityDescription {

    private static final String FIREWORK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAyZjQ4ZjM0ZDIyZGVkNzQwNGY3NmU4YTEzMmFmNWQ3OTE5YzhkY2Q1MWRmNmU3YTg1ZGRmYWM4NWFiIn19fQ==";

    public FireworkEntityDescription() {
        super();
    }

    @SuppressWarnings("unused")
    public static FireworkEntityDescription deserialize(Map<String, Object> map) {
        return new FireworkEntityDescription();
    }

    @Override
    public EntityDescription clone() {
        return new FireworkEntityDescription();
    }

    @Override
    protected ItemStack getHead() {
        return Libs.getWrapper().skullFromValue(FIREWORK_HEAD);
    }

    @Override
    protected int getTaskPeriod() {
        return 4;
    }

    @Override
    protected void spawnParticle(Location location) {
        location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location, 3, 0.1, 0.1, 0.1, 0.01);
    }

}
