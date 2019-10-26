package com.github.jummes.elytrabooster.boosts;

import java.util.List;

import com.github.jummes.elytrabooster.trails.BoostTrail;

public abstract class Boost {

	private BoostTrail trail;
	private List<String> boostActions;
	
	public Boost(BoostTrail trail, List<String> boostActions) {
		this.trail = trail;
		this.boostActions = boostActions;
	}

	public BoostTrail getTrail() {
		return trail;
	}
	
	public List<String> getBoostActions(){
		return boostActions;
	}
	
	
}
