package com.github.jummes.elytrabooster.boost;

import com.github.jummes.elytrabooster.action.AbstractAction;
import com.github.jummes.elytrabooster.boost.trail.BoostTrail;
import com.github.jummes.libs.annotation.Serializable;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class VerticalBoost extends Boost {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD)
    private double verticalVelocity;
    @Serializable(headTexture = HEAD)
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
