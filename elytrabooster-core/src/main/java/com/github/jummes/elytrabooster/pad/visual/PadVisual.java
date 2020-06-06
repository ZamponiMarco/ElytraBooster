package com.github.jummes.elytrabooster.pad.visual;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;
import org.bukkit.Location;

@Enumerable(classArray = {FireworkPadVisual.class, FlamePadVisual.class})
public abstract class PadVisual implements Model {

    protected ElytraBooster plugin;

    public PadVisual() {
        this.plugin = ElytraBooster.getInstance();
    }

    public abstract void startVisual(Location center);

    public abstract void stopVisual();

    public abstract void onBoost(Location playerLocation);

    public abstract void initializeVisual(Location center);

}
