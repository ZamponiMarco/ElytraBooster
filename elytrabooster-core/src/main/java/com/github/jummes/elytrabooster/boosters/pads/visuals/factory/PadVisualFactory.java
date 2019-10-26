package com.github.jummes.elytrabooster.boosters.pads.visuals.factory;

import org.bukkit.Location;

import com.github.jummes.elytrabooster.boosters.pads.visuals.FireworkPadVisual;
import com.github.jummes.elytrabooster.boosters.pads.visuals.FlamePadVisual;
import com.github.jummes.elytrabooster.boosters.pads.visuals.PadVisual;

public class PadVisualFactory {

	public static PadVisual buildPadVisual(String visualString, Location center) {
		switch (visualString) {
		case "flame":
			return new FlamePadVisual(center);
		case "firework":
			return new FireworkPadVisual(center);
		}
		return null;
	}

}
