package com.github.jummes.elytrabooster.outlines.factory;

import com.github.jummes.elytrabooster.outlines.BlockPortalOutline;
import com.github.jummes.elytrabooster.outlines.NoOutlinePortal;
import com.github.jummes.elytrabooster.outlines.ParticlePortalOutline;
import com.github.jummes.elytrabooster.outlines.PortalOutline;

public class PortalOutlineFactory {

	public static PortalOutline buildPortalOutline(boolean isBlock, String outlineType, String cooldownType) {
		if (!isBlock) {
			return new ParticlePortalOutline(outlineType, cooldownType);
		} else {
			return outlineType=="AIR"?new NoOutlinePortal():new BlockPortalOutline(outlineType, cooldownType);
		}
	}

}
