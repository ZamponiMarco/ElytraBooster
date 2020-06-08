package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.LocationTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.github.jummes.libs.annotation.Serializable;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.bukkit.Particle;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ParticleAction extends AbstractAction {

    private static final String TYPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY4NDczNWZjOWM3NjBlOTVlYWYxMGNlYzRmMTBlZGI1ZjM4MjJhNWZmOTU1MWVlYjUwOTUxMzVkMWZmYTMwMiJ9fX0=";
    private static final String COUNT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjdkYzNlMjlhMDkyM2U1MmVjZWU2YjRjOWQ1MzNhNzllNzRiYjZiZWQ1NDFiNDk1YTEzYWJkMzU5NjI3NjUzIn19fQ==";
    private static final String OFFSET_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDI4OTIyMTdjZThmYTg0MTI4YWJlMjY0YjVlNzFkN2VlN2U2YTlkNTgyMzgyNThlZjdkMmVmZGMzNDcifX19";
    private static final String SPEED_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTNmYzUyMjY0ZDhhZDllNjU0ZjQxNWJlZjAxYTIzOTQ3ZWRiY2NjY2Y2NDkzNzMyODliZWE0ZDE0OTU0MWY3MCJ9fX0=";
    private static final String FORCE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==";


    @Serializable(headTexture = TYPE_HEAD)
    private Particle type;
    @Serializable(headTexture = COUNT_HEAD)
    private int count;
    @Serializable(headTexture = OFFSET_HEAD)
    private double offset;
    @Serializable(headTexture = SPEED_HEAD)
    private double speed;
    @Serializable(headTexture = FORCE_HEAD)
    private boolean force;

    public ParticleAction(){
        this(Particle.FIREWORKS_SPARK, 1, 0, 0, false);
    }

    public static ParticleAction deserialize(Map<String, Object> map) {
        Particle type = Particle.valueOf((String) map.get("type"));
        int count = (int) map.get("count");
        double offset = (double) map.get("offset");
        double speed = (double) map.get("speed");
        boolean force = (boolean) map.get("force");
        return new ParticleAction(type, count, offset, speed, force);
    }

    @Override
    public void execute(Target target) {
        ((LocationTarget) target).getTarget().getWorld().spawnParticle(type, ((LocationTarget) target).getTarget(), count, offset, offset, offset, speed, null, force);
    }

    @Override
    public List<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(LocationTarget.class);
    }

}
