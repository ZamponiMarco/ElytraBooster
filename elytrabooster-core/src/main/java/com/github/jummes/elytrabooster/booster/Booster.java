package com.github.jummes.elytrabooster.booster;

import com.github.jummes.elytrabooster.manager.BoosterManager;
import org.bukkit.Location;

public interface Booster {

    public BoosterManager<?> getDataManager();

    public String getId();

    public Location getCenter();

    public void stopBoosterTask();

    public void runBoosterTask();

}
