package com.github.jummes.elytrabooster.booster;

import org.bukkit.Location;

import com.github.jummes.elytrabooster.manager.BoosterManager;

public interface Booster {

    public BoosterManager<?> getDataManager();

    public String getId();

    public Location getCenter();

    public void stopBoosterTask();

    public void runBoosterTask();

}
