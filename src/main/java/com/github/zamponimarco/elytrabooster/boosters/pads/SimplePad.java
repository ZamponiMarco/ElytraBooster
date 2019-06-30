package com.github.zamponimarco.elytrabooster.boosters.pads;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.boosters.pads.visuals.PadVisual;
import com.github.zamponimarco.elytrabooster.boosts.VerticalBoost;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class SimplePad extends AbstractPad {

	public SimplePad(ElytraBooster plugin, String id, Location center, VerticalBoost boost, PadVisual visual, int cooldown) {
		super(plugin, id, center, boost, visual, cooldown);
		runBoosterTask();
	}

}
