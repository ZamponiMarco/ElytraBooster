package com.github.zamponimarco.elytrabooster.boosts;

import java.util.List;

import com.github.zamponimarco.elytrabooster.trails.BoostTrail;

public class SimpleBoost extends Boost {

	private int boostDuration;
	private double initialVelocity;
	private double finalVelocity;

	public SimpleBoost(BoostTrail trail, List<String> boostActions, int boostDuration, double initialVelocity,
			double finalVelocity) {
		super(trail, boostActions);
		this.boostDuration = boostDuration;
		this.initialVelocity = initialVelocity;
		this.finalVelocity = finalVelocity;
	}
	
	public int getBoostDuration() {
		return boostDuration;
	}

	public double getInitialVelocity() {
		return initialVelocity;
	}

	public double getFinalVelocity() {
		return finalVelocity;
	}

}
