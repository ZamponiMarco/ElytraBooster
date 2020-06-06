package com.github.jummes.elytrabooster.boost;

import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;

import com.github.jummes.elytrabooster.action.AbstractAction;
import com.github.jummes.elytrabooster.boost.trail.BoostTrail;
import com.github.jummes.elytrabooster.boost.trail.SimpleBoostTrail;
import com.github.jummes.libs.annotation.Serializable;
import com.google.common.collect.Lists;

import lombok.Getter;

@Getter
@SerializableAs("SimpleBoost")
public class SimpleBoost extends Boost {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD)
    private int boostDuration;
    @Serializable(headTexture = HEAD)
    private double initialVelocity;
    @Serializable(headTexture = HEAD)
    private double finalVelocity;

    public SimpleBoost() {
        this(new SimpleBoostTrail(null), Lists.newArrayList(), 30, 3.0, 1.0);
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
