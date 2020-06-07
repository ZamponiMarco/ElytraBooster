package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.PlayerTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.github.jummes.libs.annotation.Serializable;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public class DamageAction extends AbstractAction {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD)
    private int amount;

    public DamageAction() {
        this(1);
    }

    public DamageAction(int amount) {
        super();
        this.amount = amount;
    }

    public static DamageAction deserialize(Map<String, Object> map) {
        int amount = (int) map.get("amount");
        return new DamageAction(amount);
    }

    @Override
    public void execute(Target target) {
        ((PlayerTarget) target).getTarget().damage(amount);
    }

    @Override
    public List<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(PlayerTarget.class);
    }

}
