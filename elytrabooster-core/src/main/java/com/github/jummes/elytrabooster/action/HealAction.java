package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.targeter.PlayerTarget;
import com.github.jummes.elytrabooster.action.targeter.Target;
import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.util.ItemUtils;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Enumerable.Child
@Enumerable.Displayable(name = "&c&lHeal", description = "gui.action.heal.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjEyNjZiNzQ4MjQyMTE1YjMwMzcwOGQ1OWNlOWQ1NTIzYjdkNzljMTNmNmRiNGViYzkxZGQ0NzIwOWViNzU5YyJ9fX0=")
public class HealAction extends AbstractAction {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkYzNlMjlhMDkyM2U1MmVjZWU2YjRjOWQ1MzNhNzllNzRiYjZiZWQ1NDFiNDk1YTEzYWJkMzU5NjI3NjUzIn19fQ==";

    @Serializable(headTexture = HEAD, description = "gui.action.heal.amount")
    @Serializable.Number(minValue = 0)
    private int amount;

    public HealAction() {
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

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(Libs.getWrapper().skullFromValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjEyNjZiNzQ4MjQyMTE1YjMwMzcwOGQ1OWNlOWQ1NTIzYjdkNzljMTNmNmRiNGViYzkxZGQ0NzIwOWViNzU5YyJ9fX0="),
                "&6&lHeal: &c" + amount, Libs.getLocale().getList("gui.action.description"));
    }

}
