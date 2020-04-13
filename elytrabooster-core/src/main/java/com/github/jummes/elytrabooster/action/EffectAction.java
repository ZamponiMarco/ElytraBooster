package com.github.jummes.elytrabooster.action;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.jummes.elytrabooster.core.ElytraBooster;

public class EffectAction extends AbstractAction {

    private Player target;
    private PotionEffectType type;
    private int duration;
    private int level;
    private boolean particles;
    private boolean ambient;
    private boolean icon;

    public EffectAction(ElytraBooster plugin, Map<String, String> parameters) {
        super(plugin, parameters);
    }

    @Override
    protected void parseParameters(Map<String, String> parameters) {
        target = Bukkit.getPlayer(parameters.get("player"));

        type = PotionEffectType.getByName(parameters.getOrDefault("type", "INCREASE_DAMAGE").toUpperCase());

        duration = Integer.valueOf(parameters.getOrDefault("duration", "20"));

        level = Integer.valueOf(parameters.getOrDefault("level", "0"));

        particles = Boolean.valueOf(parameters.getOrDefault("particles", "true"));

        ambient = Boolean.valueOf(parameters.getOrDefault("ambient", "true"));

        icon = Boolean.valueOf(parameters.getOrDefault("icon", "true"));
    }

    @Override
    public void executeAction() {
        target.addPotionEffect(new PotionEffect(type, duration, level, ambient, particles, icon));
    }

}
