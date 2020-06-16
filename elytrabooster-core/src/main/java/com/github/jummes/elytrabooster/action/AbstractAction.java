package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.targeter.Target;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;
import org.apache.commons.lang.ClassUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Enumerable.Parent(classArray = {DamageAction.class, EffectAction.class, HealAction.class, MessageAction.class, ParticleAction.class, SoundAction.class})
public abstract class AbstractAction implements Model {

    protected ElytraBooster plugin;

    public AbstractAction() {
        this.plugin = ElytraBooster.getInstance();
    }

    public void executeAction(Target target) {
        if (getPossibleTargets().stream().anyMatch(clazz -> ClassUtils.isAssignable(target.getClass(), clazz))) {
            execute(target);
        }
    }

    protected abstract void execute(Target target);

    public abstract List<Class<? extends Target>> getPossibleTargets();

    @Override
    public ItemStack getGUIItem() {
        return new ItemStack(Material.PAPER);
    }
}
