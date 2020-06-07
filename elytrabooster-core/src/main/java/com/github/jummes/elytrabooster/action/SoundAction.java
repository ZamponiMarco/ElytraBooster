package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.LocationTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.google.common.collect.Lists;
import org.bukkit.*;

import java.util.List;
import java.util.Map;

public class SoundAction extends AbstractAction {

    private Location location;
    private Sound type;
    private SoundCategory category;
    private float pitch;
    private float volume;

    @Override
    public void execute(Target target) {
        location.getWorld().playSound(location, type, category, volume, pitch);
    }

    @Override
    public List<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(LocationTarget.class);
    }

}
