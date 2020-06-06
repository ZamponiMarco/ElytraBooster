package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

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

        duration = Integer.parseInt(parameters.getOrDefault("duration", "20"));

        level = Integer.parseInt(parameters.getOrDefault("level", "0"));

        particles = Boolean.parseBoolean(parameters.getOrDefault("particles", "true"));

        ambient = Boolean.parseBoolean(parameters.getOrDefault("ambient", "true"));

        icon = Boolean.parseBoolean(parameters.getOrDefault("icon", "true"));
    }

    @Override
    public void executeAction() {
        target.addPotionEffect(new PotionEffect(type, duration, level, ambient, particles, icon));
    }

}
