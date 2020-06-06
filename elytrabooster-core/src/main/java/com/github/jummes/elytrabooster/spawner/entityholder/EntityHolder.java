package com.github.jummes.elytrabooster.spawner.entityholder;

import com.github.jummes.elytrabooster.boost.SimpleBoost;
import com.github.jummes.elytrabooster.spawner.entityholder.entity.ActiveEntity;
import com.github.jummes.elytrabooster.spawner.entityholder.entity.EntityDescription;
import com.github.jummes.elytrabooster.spawner.entityholder.entity.FireworkEntityDescription;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import lombok.Getter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class EntityHolder implements Model {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD)
    private EntityDescription entityDescription;
    @Serializable(headTexture = HEAD)
    private int maxEntities;
    @Serializable(headTexture = HEAD)
    private SimpleBoost boost;
    private List<ActiveEntity> entities;

    public EntityHolder() {
        this(new FireworkEntityDescription(), 2, new SimpleBoost());
    }

    public EntityHolder(EntityDescription entityDescription, int maxEntities, SimpleBoost boost) {
        this.entityDescription = entityDescription;
        this.maxEntities = maxEntities;
        this.boost = boost;
        this.entities = new ArrayList<>();
    }

    public static EntityHolder deserialize(Map<String, Object> map) {
        EntityDescription entityDescription = (EntityDescription) map.get("entityDescription");
        int maxEntities = (int) map.get("maxEntities");
        SimpleBoost boost = (SimpleBoost) map.get("boost");
        return new EntityHolder(entityDescription, maxEntities, boost);
    }

    public void spawnEntity(Location center) {
        if (entities.size() < maxEntities) {
            entities.add(new ActiveEntity(entityDescription, new LocationWrapper(center), this));
        }
    }

    public void despawn(ActiveEntity entity) {
        entities.remove(entity);
    }

    public void despawnAll() {
        entities.forEach(ActiveEntity::entityDespawn);
        entities.clear();
    }

}
