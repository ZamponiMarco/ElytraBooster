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
@Enumerable.Displayable(name = "&c&lMushroom Entity", description = "gui.spawner.entityHolder.entityDescription.mushroom.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzU3MTk1MmEzNWMzMTYzYjhjMzNhMDkxOGQ2ZTlhNDUzM2Y2MjA1M2FkNGU2Y2ZjYjFmYTI3ZjU1MWFlZjIifX19==")
public class MushroomEntityDescription extends EntityDescription {

    private static final String MUSHROOM_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzU3MTk1MmEzNWMzMTYzYjhjMzNhMDkxOGQ2ZTlhNDUzM2Y2MjA1M2FkNGU2Y2ZjYjFmYTI3ZjU1MWFlZjIifX19==";

    public MushroomEntityDescription() {
        super();
    }

    @SuppressWarnings("unused")
    public static MushroomEntityDescription deserialize(Map<String, Object> map) {
        return new MushroomEntityDescription();
    }

    @Override
    protected ItemStack getHead() {
        return Libs.getWrapper().skullFromValue(MUSHROOM_HEAD);
    }

    @Override
    protected int getTaskPeriod() {
        return 5;
    }

    @Override
    protected void spawnParticle(Location location) {
        location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,
                location.clone().add(new Vector(0, 0.15, 0)), 1, 0.15, 0.15, 0.15, 0.1);
    }

    @Override
    public EntityDescription clone() {
        return new MushroomEntityDescription();
    }
}
