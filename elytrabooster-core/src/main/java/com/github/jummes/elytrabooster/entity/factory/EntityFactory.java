package com.github.jummes.elytrabooster.entities.factory;

import org.bukkit.Location;

import com.github.jummes.elytrabooster.boosts.Boost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.entities.AbstractEntity;
import com.github.jummes.elytrabooster.entityholders.EntityHolder;

public class EntityFactory {

	public static AbstractEntity buildEntity(Class<? extends AbstractEntity> entityClass, ElytraBooster plugin, EntityHolder holder, Location location, Boost boost) {
		try {
			return entityClass.getConstructor(ElytraBooster.class, EntityHolder.class, Location.class, Boost.class).newInstance(plugin, holder, location, boost);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	
}
