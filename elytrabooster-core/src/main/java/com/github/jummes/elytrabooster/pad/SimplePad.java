package com.github.jummes.elytrabooster.pad;

import com.github.jummes.elytrabooster.boost.VerticalBoost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.pad.visual.PadVisual;
import org.bukkit.Location;

public class SimplePad extends AbstractPad {

    public SimplePad(ElytraBooster plugin, String id, Location center, VerticalBoost boost, PadVisual visual, int cooldown) {
        super(plugin, id, center, boost, visual, cooldown);
        runBoosterTask();
    }

}
