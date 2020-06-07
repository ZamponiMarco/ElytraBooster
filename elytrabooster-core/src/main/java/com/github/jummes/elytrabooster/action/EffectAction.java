package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.PlayerTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class EffectAction extends AbstractAction {

    private PotionEffectType type;
    private int duration;
    private int level;
    private boolean particles;
    private boolean ambient;
    private boolean icon;

    public EffectAction(){this(PotionEffectType.INCREASE_DAMAGE, 20, 0, true, true, true);}

    public static EffectAction deserialize(Map<String, Object> map) {
        PotionEffectType type = PotionEffectType.getByName((String) map.get("type"));
        int duration = (int) map.get("duration");
        int level = (int) map.get("level");
        boolean particles = (boolean) map.get("particles");
        boolean ambient = (boolean) map.get("ambient");
        boolean icon = (boolean) map.get("icon");
        return new EffectAction(type, duration, level, particles, ambient, icon);
    }

    @Override
    public void execute(Target target) {
        ((PlayerTarget) target).getTarget().addPotionEffect(new PotionEffect(type, duration, level, ambient, particles, icon));
    }

    @Override
    public List<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(PlayerTarget.class);
    }

}
