package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.targeter.PlayerTarget;
import com.github.jummes.elytrabooster.action.targeter.Target;
import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.util.ItemUtils;
import com.google.common.collect.Lists;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lDamage", description = "gui.action.damage.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVlMTE4ZWRkYWVlMGRmYjJjYmMyYzNkNTljMTNhNDFhN2Q2OGNjZTk0NWU0MjE2N2FhMWRjYjhkMDY3MDUxNyJ9fX0=")
public class DamageAction extends AbstractAction {

    private static final String AMOUNT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkYzNlMjlhMDkyM2U1MmVjZWU2YjRjOWQ1MzNhNzllNzRiYjZiZWQ1NDFiNDk1YTEzYWJkMzU5NjI3NjUzIn19fQ==";

    @Serializable(headTexture = AMOUNT_HEAD, description = "gui.action.damage.amount")
    @Serializable.Number(minValue = 0)
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

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(Libs.getWrapper().skullFromValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVlMTE4ZWRkYWVlMGRmYjJjYmMyYzNkNTljMTNhNDFhN2Q2OGNjZTk0NWU0MjE2N2FhMWRjYjhkMDY3MDUxNyJ9fX0="),
                "&6&lDamage: &c" + amount, Libs.getLocale().getList("gui.action.description"));
    }

}
