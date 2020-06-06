package com.github.jummes.elytrabooster.boost;

import com.github.jummes.elytrabooster.action.AbstractAction;
import com.github.jummes.elytrabooster.boost.trail.BoostTrail;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class Boost implements Model {

    @Serializable()
    private BoostTrail trail;
    @Serializable()
    private List<AbstractAction> boostActions;

    public Boost(BoostTrail trail, List<AbstractAction> boostActions) {
        this.trail = trail;
        this.boostActions = boostActions;
    }

}
