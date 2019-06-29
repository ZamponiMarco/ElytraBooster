package com.github.zamponimarco.elytrabooster.boosts;

import java.util.List;

import com.github.zamponimarco.elytrabooster.trails.BoostTrail;

public class VerticalBoost extends Boost {

	private double verticalVelocity;
	private double horizontalVelocity;
	
	public VerticalBoost(BoostTrail trail, List<String> boostActions, double verticalVelocity, double horizontalVelocity) {
		super(trail, boostActions);
		this.verticalVelocity = verticalVelocity;
		this.horizontalVelocity = horizontalVelocity;
	}

	public double getVerticalVelocity() {
		return verticalVelocity;
	}

	public double getHorizontalVelocity() {
		return horizontalVelocity;
	}

}
