package com.github.jummes.elytrabooster.boost;

import com.github.jummes.elytrabooster.action.AbstractAction;
import com.github.jummes.elytrabooster.boost.trail.BoostTrail;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class Boost implements Model {

    private static final String TRAIL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzYxOWJmNjI4NjNlYzExNTc3ZDZlZjY1ZWZkYzNmOWRlNGRmNDE0MjAyZWQxZmYxZGU5ZWM3NmI2MWEzZjY2NyJ9fX0========";

    @Serializable(headTexture = TRAIL_HEAD)
    private BoostTrail trail;
    @Serializable(headTexture = TRAIL_HEAD)
    private List<AbstractAction> boostActions;

    public Boost(BoostTrail trail, List<AbstractAction> boostActions) {
        this.trail = trail;
        this.boostActions = boostActions;
    }

}
