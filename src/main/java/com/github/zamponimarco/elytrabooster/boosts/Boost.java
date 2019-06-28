package com.github.zamponimarco.elytrabooster.boosts;

import java.util.List;

import com.github.zamponimarco.elytrabooster.trails.BoostTrail;

public abstract class Boost {

	private int boostDuration;
	private double initialVelocity;
	private double finalVelocity;
	private BoostTrail trail;
	private List<String> boostActions;
	
	public Boost(int boostDuration, double initialVelocity, double finalVelocity, BoostTrail trail, List<String> boostActions) {
		this.boostDuration = boostDuration;
		this.initialVelocity = initialVelocity;
		this.finalVelocity = finalVelocity;
		this.trail = trail;
		this.boostActions = boostActions;
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

	public BoostTrail getTrail() {
		return trail;
	}
	
	public List<String> getBoostActions(){
		return boostActions;
	}
	
	
}
