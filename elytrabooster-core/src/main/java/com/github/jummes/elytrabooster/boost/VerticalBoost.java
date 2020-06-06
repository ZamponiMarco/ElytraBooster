package com.github.jummes.elytrabooster.boost;

import java.util.List;
import java.util.Map;

import com.github.jummes.elytrabooster.action.AbstractAction;
import com.github.jummes.elytrabooster.boost.trail.BoostTrail;

import lombok.Getter;

@Getter
public class VerticalBoost extends Boost {

    private double verticalVelocity;
    private double horizontalVelocity;

    public VerticalBoost(BoostTrail trail, List<AbstractAction> boostActions, double verticalVelocity, double horizontalVelocity) {
        super(trail, boostActions);
        this.verticalVelocity = verticalVelocity;
        this.horizontalVelocity = horizontalVelocity;
    }

    @SuppressWarnings("unchecked")
    public static VerticalBoost deserialize(Map<String, Object> map) {
        BoostTrail trail = (BoostTrail) map.get("trail");
        List<AbstractAction> boostActions = (List<AbstractAction>) map.get("boostActions");
        double verticalVelocity = (double) map.get("verticalVelocity");
        double horizontalVelocity = (double) map.get("horizontalVelocity");
        return new VerticalBoost(trail, boostActions, verticalVelocity, horizontalVelocity);
    }

}
