package com.github.jummes.elytrabooster.entityholders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

import com.github.jummes.elytrabooster.boosts.SimpleBoost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.entities.AbstractEntity;
import com.github.jummes.elytrabooster.entities.factory.EntityFactory;

public class EntityHolder {

	private ElytraBooster plugin;
	private Class<? extends AbstractEntity> entityClass;
	private List<AbstractEntity> entities;
	private int maxEntities;
	private SimpleBoost boost;

	public EntityHolder(ElytraBooster plugin, Class<? extends AbstractEntity> entityClass, int maxEntities, SimpleBoost boost,
			String spawner) {
		this.plugin = plugin;
		this.entityClass = entityClass;
		this.maxEntities = maxEntities;
		this.boost = boost;
		this.entities = new ArrayList<AbstractEntity>();
	}

	public void spawnEntity(Location center, double minRadius, double maxRadius) {
		if (entities.size() < maxEntities) {
			Random r = new Random();
			double multiply = maxRadius - minRadius;
			double x, y, z;
			Location loc;
			do {
				double randomX = r.nextDouble() * 2 - 1;
				x = center.getX() + randomX * multiply + (Math.signum(randomX) > 0 ? minRadius : -minRadius);
				double randomY = r.nextDouble() * 2 - 1;
				y = center.getY() + randomY * multiply + (Math.signum(randomX) > 0 ? minRadius : -minRadius);
				double randomZ = r.nextDouble() * 2 - 1;
				z = center.getZ() + randomZ * multiply + (Math.signum(randomX) > 0 ? minRadius : -minRadius);
				loc = new Location(center.getWorld(), x, y, z);
			} while (loc.getBlock().getType() != Material.AIR);
			entities.add(EntityFactory.buildEntity(entityClass, plugin, this, loc, boost));
		}
	}

	public void despawnAll() {
		entities.forEach(entity -> entity.entityDespawn());
		entities.clear();
	}

	public List<AbstractEntity> getEntities() {
		return entities;
	}

	public String getEntity() {
		String name = entityClass.getName();
		int start = name.lastIndexOf(".") + 1;
		int end = name.lastIndexOf("Entity");
		return entityClass.getName().substring(start, end).toLowerCase();
	}

	public int getMaxEntities() {
		return maxEntities;
	}

	public void despawn(AbstractEntity entity) {
		entities.remove(entity);
	}

	public SimpleBoost getBoost() {
		return boost;
	}

}
