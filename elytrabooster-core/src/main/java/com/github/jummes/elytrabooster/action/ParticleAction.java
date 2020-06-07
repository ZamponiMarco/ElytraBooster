package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.LocationTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.List;
import java.util.Map;

public class ParticleAction extends AbstractAction {

    private Location location;
    private Particle type;
    private int count;
    private double offset;
    private double speed;
    private boolean force;

    @Override
    public void execute(Target target) {
        location.getWorld().spawnParticle(type, location, count, offset, offset, offset, speed, null, force);
    }

    @Override
    public List<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(LocationTarget.class);
    }

}
