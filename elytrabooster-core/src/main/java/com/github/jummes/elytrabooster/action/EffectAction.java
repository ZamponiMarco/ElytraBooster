package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.PlayerTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.google.common.collect.Lists;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

public class EffectAction extends AbstractAction {

    private Player target;
    private PotionEffectType type;
    private int duration;
    private int level;
    private boolean particles;
    private boolean ambient;
    private boolean icon;

    @Override
    public void execute(Target target) {
        this.target.addPotionEffect(new PotionEffect(type, duration, level, ambient, particles, icon));
    }

    @Override
    public List<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(PlayerTarget.class);
    }

}
