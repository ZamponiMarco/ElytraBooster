package com.github.jummes.elytrabooster.pad;

import org.bukkit.Location;

import com.github.jummes.elytrabooster.boost.VerticalBoost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.pad.visual.PadVisual;

public class SimplePad extends AbstractPad {

    public SimplePad(ElytraBooster plugin, String id, Location center, VerticalBoost boost, PadVisual visual, int cooldown) {
        super(plugin, id, center, boost, visual, cooldown);
        runBoosterTask();
    }

}
