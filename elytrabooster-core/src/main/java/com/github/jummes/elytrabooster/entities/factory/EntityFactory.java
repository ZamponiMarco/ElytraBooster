package com.github.jummes.elytrabooster.entities.factory;

import org.bukkit.Location;

import com.github.jummes.elytrabooster.boosts.Boost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.entities.Entity;
import com.github.jummes.elytrabooster.entityholders.EntityHolder;

public class EntityFactory {

	public static Entity buildEntity(Class<? extends Entity> entityClass, ElytraBooster plugin, EntityHolder holder, Location location, Boost boost) {
		try {
			return entityClass.getConstructor(ElytraBooster.class, EntityHolder.class, Location.class, Boost.class).newInstance(plugin, holder, location, boost);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	
}
