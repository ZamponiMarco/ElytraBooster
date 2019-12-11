package com.github.jummes.elytrabooster.entities;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import com.github.jummes.elytrabooster.boosts.Boost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.entityholders.EntityHolder;

public class MushroomEntity extends AbstractEntity {

	private static final String MUSHROOM_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzU3MTk1MmEzNWMzMTYzYjhjMzNhMDkxOGQ2ZTlhNDUzM2Y2MjA1M2FkNGU2Y2ZjYjFmYTI3ZjU1MWFlZjIifX19==";

	private Item item;
	private int effectTaskNumber;

	public MushroomEntity(ElytraBooster plugin, EntityHolder holder, Location location, Boost boost) {
		super(plugin, holder, location, boost);
		spawn();
		runEntityTask();
	}

	@Override
	public void spawn() {
		item = (Item) location.getWorld().spawnEntity(location, EntityType.DROPPED_ITEM);
		item.setGravity(false);
		item.setVelocity(new Vector());
		item.setPickupDelay(32767);
		item.setInvulnerable(true);
		item.setItemStack(ElytraBooster.getInstance().getWrapper().skullFromValue(MUSHROOM_HEAD));
		runEffectTask();
	}

	@Override
	public void entityDespawn() {
		item.remove();
		plugin.getServer().getScheduler().cancelTask(effectTaskNumber);
	}

	private void runEffectTask() {
		this.effectTaskNumber = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
			if (item.isDead()) {
				entityDespawn();
				spawn();
			} else {
				location.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location.clone().add(new Vector(0, 0.15, 0)), 1, 0.15,
						0.15, 0.15, 0.1);
			}
		}, 0, 5).getTaskId();
	}

	@Override
	public void onActivation() {
	}
}
