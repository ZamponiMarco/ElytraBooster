package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.LocationTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.bukkit.Particle;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ParticleAction extends AbstractAction {

    private Particle type;
    private int count;
    private double offset;
    private double speed;
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
