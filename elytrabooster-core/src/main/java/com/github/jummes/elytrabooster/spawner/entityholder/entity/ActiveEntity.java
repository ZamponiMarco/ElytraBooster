package com.github.jummes.elytrabooster.spawner.entityholder.entity;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.event.PlayerSimpleBoostEvent;
import com.github.jummes.elytrabooster.spawner.entityholder.EntityHolder;
import com.github.jummes.elytrabooster.spawner.entityholder.entity.description.EntityDescription;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActiveEntity {

    private final int CHECK_INTERVAL;
    private final double CHECK_RADIUS = 1.0;
    private final LocationWrapper location;
    protected ElytraBooster plugin;
    protected EntityHolder holder;
    private EntityDescription description;
    private int checkTaskNumber;

    public ActiveEntity(EntityDescription description, LocationWrapper location, EntityHolder holder) {
        this.plugin = ElytraBooster.getInstance();
        try {
            this.description = (EntityDescription) description.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        this.location = location;
        this.holder = holder;
        CHECK_INTERVAL = plugin.getConfig().getInt("spawnerCheckInterval", 1);
        spawn();
        runEntityTask();
    }

    public void runEntityTask() {
        checkTaskNumber = plugin.getServer().getScheduler()
                .runTaskTimer(plugin, this::checkPlayersPassing, 0, CHECK_INTERVAL).getTaskId();
    }

    private void checkPlayersPassing() {
        plugin.getStatusMap().keySet().stream()
                .filter(player -> !plugin.getStatusMap().get(player) && player.hasPermission("eb.boosters.boost")
                        && player.getWorld().equals(location.getWrapped().getWorld())
                        && player.getLocation().distance(location.getWrapped()) <= CHECK_RADIUS)
                .forEach(this::boostPlayer);
    }

    public void boostPlayer(Player player) {
        PlayerSimpleBoostEvent event = new PlayerSimpleBoostEvent(player);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            holder.getBoost().boostPlayer(player);
        }
        holderDespawn();
    }

    public void holderDespawn() {
        entityDespawn();
        holder.despawn(this);
    }

    public void spawn() {
        description.spawn(location.getWrapped());
    }

    public void entityDespawn() {
        description.entityDespawn();
        plugin.getServer().getScheduler().cancelTask(checkTaskNumber);
    }

}
