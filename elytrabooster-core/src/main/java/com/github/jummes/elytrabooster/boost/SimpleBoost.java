package com.github.jummes.elytrabooster.boost;

import com.github.jummes.elytrabooster.action.AbstractAction;
import com.github.jummes.elytrabooster.boost.trail.BoostTrail;
import com.github.jummes.elytrabooster.boost.trail.SimpleBoostTrail;
import com.github.jummes.libs.annotation.Serializable;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Particle;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.List;
import java.util.Map;

@Getter
@SerializableAs("SimpleBoost")
public class SimpleBoost extends Boost {

    private static final String INITIAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJjZGM2Zjg4Yzg1M2U4MzE0OTVhMTc0NmViMjdhYTYxYjlkYWMyZTg2YTQ0Yjk1MjJlM2UyYjdkYzUifX19=";
    private static final String FINAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI5MjBjMzgxNWI5YzQ1OTJlNjQwOGUzMjIzZjMxMzUxZmM1NzhmMzU1OTFiYzdmOWJlYmQyMWVmZDhhMDk3In19fQ===";
    private static final String BOOST_DURATION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Q1MWM4M2NjMWViY2E1YTFiNmU2Nzk0N2UyMGI0YTJhNmM5ZWZlYTBjZjQ2OTI5NDQ4ZTBlMzc0MTZkNTgzMyJ9fX0====";

    @Serializable(headTexture = BOOST_DURATION_HEAD)
    private int boostDuration;
    @Serializable(headTexture = INITIAL_VELOCITY_HEAD)
    private double initialVelocity;
    @Serializable(headTexture = FINAL_VELOCITY_HEAD)
    private double finalVelocity;

    public SimpleBoost() {
        this(new SimpleBoostTrail(Particle.FIREWORKS_SPARK), Lists.newArrayList(), 30, 3.0, 1.0);
    }

    public SimpleBoost(BoostTrail trail, List<AbstractAction> boostActions, int boostDuration, double initialVelocity,
                       double finalVelocity) {
        super(trail, boostActions);
        this.boostDuration = boostDuration;
        this.initialVelocity = initialVelocity;
        this.finalVelocity = finalVelocity;
    }

    @SuppressWarnings("unchecked")
    public static SimpleBoost deserialize(Map<String, Object> map) {
        BoostTrail trail = (BoostTrail) map.get("trail");
        List<AbstractAction> boostActions = (List<AbstractAction>) map.get("boostActions");
        int boostDuration = (int) map.get("boostDuration");
        double initialVelocity = (double) map.get("initialVelocity");
        double finalVelocity = (double) map.get("finalVelocity");
        return new SimpleBoost(trail, boostActions, boostDuration, initialVelocity, finalVelocity);
    }

}
