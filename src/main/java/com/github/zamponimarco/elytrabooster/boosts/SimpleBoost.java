package com.github.zamponimarco.elytrabooster.boosts;

import java.util.List;

import com.github.zamponimarco.elytrabooster.trails.BoostTrail;

public class SimpleBoost extends Boost {

	public SimpleBoost(int boostDuration, double initialVelocity, double finalVelocity, BoostTrail trail, List<String> boostActions) {
		super(boostDuration, initialVelocity, finalVelocity, trail, boostActions);
	}

}
