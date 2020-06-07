package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.PlayerTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class HealAction extends AbstractAction {

    private int amount;
    
    public HealAction(){
        this(1);
    }

    public static HealAction deserialize(Map<String, Object> map) {
        int amount = (int) map.get("amount");
        return new HealAction(amount);
    }

    @Override
    public void execute(Target target) {
        healPlayer(((PlayerTarget) target).getTarget());
    }

    @Override
    public List<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(PlayerTarget.class);
    }

    private void healPlayer(Player p) {
        double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double currHealth = p.getHealth();

        p.setHealth(Math.min(currHealth + amount, maxHealth));
    }

}
