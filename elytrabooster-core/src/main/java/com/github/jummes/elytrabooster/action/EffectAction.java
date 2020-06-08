package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.PlayerTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.annotation.Serializable;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class EffectAction extends AbstractAction {

    private static final String TYPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzJhN2RjYmY3ZWNhNmI2ZjYzODY1OTFkMjM3OTkxY2ExYjg4OGE0ZjBjNzUzZmY5YTMyMDJjZjBlOTIyMjllMyJ9fX0=";
    private static final String DURATION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE2YWU1YjM0YzRmNzlhNWY5ZWQ2Y2NjMzNiYzk4MWZjNDBhY2YyYmZjZDk1MjI2NjRmZTFjNTI0ZDJlYjAifX19";
    private static final String LEVEL_HEAD= "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODIxNmVlNDA1OTNjMDk4MWVkMjhmNWJkNjc0ODc5NzgxYzQyNWNlMDg0MWI2ODc0ODFjNGY3MTE4YmI1YzNiMSJ9fX0=";
    private static final String PARTICLE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY4NDczNWZjOWM3NjBlOTVlYWYxMGNlYzRmMTBlZGI1ZjM4MjJhNWZmOTU1MWVlYjUwOTUxMzVkMWZmYTMwMiJ9fX0=";
    private static final String AMBIENT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWY1NzE5MmIxOTRjNjU4YWFhODg4MTY4NDhjYmNlN2M3NDk0NjZhNzkyYjhhN2UxZDNmYWZhNDFjNDRmMzQxMiJ9fX0=";
    private static final String ICON_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDU5MWYwNGJiNmQ1MjQ5MTFhZGRhNzc1YWYyNDRmODZhOTVkYjE4N2UzMWI4YTNiMTAzYWQ4MGZjNWIyMjU2MCJ9fX0=";

    @Serializable(headTexture = TYPE_HEAD)
    private PotionEffectType type;
    @Serializable(headTexture = DURATION_HEAD)
    private int duration;
    @Serializable(headTexture = LEVEL_HEAD)
    private int level;
    @Serializable(headTexture = PARTICLE_HEAD)
    private boolean particles;
    @Serializable(headTexture = AMBIENT_HEAD)
    private boolean ambient;
    @Serializable(headTexture = ICON_HEAD)
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
