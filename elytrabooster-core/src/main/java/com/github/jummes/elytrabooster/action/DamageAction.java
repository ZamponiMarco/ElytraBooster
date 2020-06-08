package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.PlayerTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.github.jummes.libs.annotation.Serializable;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public class DamageAction extends AbstractAction {

    private static final String AMOUNT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkYzNlMjlhMDkyM2U1MmVjZWU2YjRjOWQ1MzNhNzllNzRiYjZiZWQ1NDFiNDk1YTEzYWJkMzU5NjI3NjUzIn19fQ==";

    @Serializable(headTexture = AMOUNT_HEAD, description = "gui.action.damage.amount")
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
