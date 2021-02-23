package com.github.jummes.elytrabooster.spawner.entityholder.entity.description;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.util.particle.Particle;
import com.github.jummes.libs.model.wrapper.ItemStackWrapper;
import com.github.jummes.libs.model.wrapper.VectorWrapper;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(description = "gui.spawner.entityHolder.entityDescription.custom.description")
public class CustomEntityDescription extends EntityDescription {

    private static final String PARTICLE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY4NDczNWZjOWM3NjBlOTVlYWYxMGNlYzRmMTBlZGI1ZjM4MjJhNWZmOTU1MWVlYjUwOTUxMzVkMWZmYTMwMiJ9fX0=";
    private static final String PERIOD_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmZlOGNmZjc1ZjdkNDMzMjYwYWYxZWNiMmY3NzNiNGJjMzgxZDk1MWRlNGUyZWI2NjE0MjM3NzlhNTkwZTcyYiJ9fX0=";
    private static final String MOVE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWFjYWNiODM2YzVlNDI4YjQ5YjVkMjI0Y2FiMjI4MjhlZmUyZjZjNzA0Zjc1OTM2NGQ3MWM2NTZlMzAxNDIwIn19fQ==";

    @Serializable(displayItem = "getDisplayItem", description = "gui.spawner.entityHolder.entityDescription.custom.item")
    private ItemStackWrapper item;
    @Serializable(headTexture = PERIOD_HEAD, description = "gui.spawner.entityHolder.entityDescription.custom.period")
    private int taskPeriod;
    @Serializable(headTexture = PARTICLE_HEAD, description = "gui.spawner.entityHolder.entityDescription.custom.particle")
    private Particle particle;
    @Serializable(headTexture = MOVE_HEAD, description = "gui.spawner.entityHolder.entityDescription.custom.move")
    private VectorWrapper moveParticle;

    public CustomEntityDescription() {
        this(new ItemStackWrapper(), 5, new Particle(), new VectorWrapper());
    }

    public CustomEntityDescription(ItemStackWrapper item, int taskPeriod, Particle particle, VectorWrapper moveParticle) {
        this.item = item;
        this.taskPeriod = taskPeriod;
        this.particle = particle;
        this.moveParticle = moveParticle;
    }

    public CustomEntityDescription(Map<String, Object> map) {
        this.item = (ItemStackWrapper) map.getOrDefault("item", new ItemStackWrapper());
        this.taskPeriod = (int) map.getOrDefault("taskPeriod", 5);
        this.particle = (Particle) map.getOrDefault("particle", new Particle());
        this.moveParticle = (VectorWrapper) map.getOrDefault("moveParticle", new VectorWrapper());
    }

    @Override
    public EntityDescription clone() {
        return new CustomEntityDescription(item.clone(), taskPeriod, particle.clone(), moveParticle.clone());
    }

    public ItemStack getDisplayItem() {
        return item.getWrapped().clone();
    }

    @Override
    protected ItemStack getHead() {
        return item.getWrapped();
    }

    @Override
    protected int getTaskPeriod() {
        return taskPeriod;
    }

    @Override
    protected void spawnParticle(Location location) {
        particle.spawnParticle(location.clone().add(moveParticle.getWrapped()));
    }
}
