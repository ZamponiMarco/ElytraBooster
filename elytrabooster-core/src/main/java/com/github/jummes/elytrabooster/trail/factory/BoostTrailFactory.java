package com.github.jummes.elytrabooster.trails.factory;

import com.github.jummes.elytrabooster.trails.BoostTrail;
import com.github.jummes.elytrabooster.trails.HelixBoostTrail;
import com.github.jummes.elytrabooster.trails.NoBoostTrail;
import com.github.jummes.elytrabooster.trails.RainbowBoostTrail;
import com.github.jummes.elytrabooster.trails.SimpleBoostTrail;

public class BoostTrailFactory {

	public static BoostTrail buildBoostTrail(String trailString) {
		
		if(trailString == null) {
			trailString = "";
		}
		
		String[] trailArray = trailString.split(":");
		String trailType = trailArray[0];
		String trailParticle = trailArray.length >= 2? trailArray[1]:null;
		
		switch(trailType) {
		case "none":
			return new NoBoostTrail();
		case "rainbow":
			return new RainbowBoostTrail();
		case "helix":
			return new HelixBoostTrail(trailParticle);
		case "simple":
		default:
			return new SimpleBoostTrail(trailParticle);
		}
	}

}
