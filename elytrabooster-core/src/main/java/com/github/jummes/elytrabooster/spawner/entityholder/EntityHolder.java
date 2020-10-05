package com.github.jummes.elytrabooster.spawner.entityholder;

import com.github.jummes.elytrabooster.boost.SimpleBoost;
import com.github.jummes.elytrabooster.spawner.entityholder.entity.ActiveEntity;
import com.github.jummes.elytrabooster.spawner.entityholder.entity.description.EntityDescription;
import com.github.jummes.elytrabooster.spawner.entityholder.entity.description.FireworkEntityDescription;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class EntityHolder implements Model {

    private static final String DESCRIPTION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTZkNTU0YWQ1ZTBkYzYwMWVmYmI5MjVkMTM0MjRjY2VhNTMyYzgzMWE5MGI5Y2E3M2Q1ZTkzYWI2ZGJjNWRhZiJ9fX0=";
    private static final String MAX_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkYzNlMjlhMDkyM2U1MmVjZWU2YjRjOWQ1MzNhNzllNzRiYjZiZWQ1NDFiNDk1YTEzYWJkMzU5NjI3NjUzIn19fQ==";
    private static final String BOOST_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTNmYzUyMjY0ZDhhZDllNjU0ZjQxNWJlZjAxYTIzOTQ3ZWRiY2NjY2Y2NDkzNzMyODliZWE0ZDE0OTU0MWY3MCJ9fX0=";

    @Serializable(headTexture = DESCRIPTION_HEAD, description = "gui.spawner.entityHolder.entityDescription.description", recreateTooltip = true)
    private EntityDescription entityDescription;
    @Serializable(headTexture = MAX_HEAD, description = "gui.spawner.entityHolder.maxEntities")
    @Serializable.Number(minValue = 0)
    private int maxEntities;
    @Serializable(headTexture = BOOST_HEAD, description = "gui.spawner.entityHolder.boost")
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
