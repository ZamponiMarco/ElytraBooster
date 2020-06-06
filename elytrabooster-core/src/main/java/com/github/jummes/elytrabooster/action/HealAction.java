package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.Map;

public class HealAction extends AbstractAction {

    private Player target;
    private int amount;

    public HealAction(ElytraBooster plugin, Map<String, String> parameters) {
        super(plugin, parameters);
    }

    @Override
    protected void parseParameters(Map<String, String> parameters) {
        target = Bukkit.getPlayer(parameters.get("player"));

        amount = Integer.parseInt(parameters.getOrDefault("amount", "2"));
    }

    @Override
    public void executeAction() {
        healPlayer(target);
    }

    private void healPlayer(Player p) {
        double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double currHealth = p.getHealth();

        p.setHealth(Math.min(currHealth + amount, maxHealth));
    }

}
