package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.LocationTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.bukkit.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SoundAction extends AbstractAction {

    private Sound type;
    private SoundCategory category;
    private float pitch;
    private float volume;

    public SoundAction(){
        this(Sound.BLOCK_ANVIL_BREAK, SoundCategory.MASTER, 1f, 10f);
    }

    public static SoundAction deserialize(Map<String, Object> map) {
        Sound type = Sound.valueOf((String) map.get("type"));
        SoundCategory category = SoundCategory.valueOf((String) map.get("category"));
        float pitch = (float) map.get("pitch");
        float volume = (float) map.get("volume");
        return new SoundAction(type, category, pitch, volume);
    }

    @Override
    public void execute(Target target) {
        ((LocationTarget)target).getTarget().getWorld().playSound(((LocationTarget)target).getTarget(), type, category, volume, pitch);
    }

    @Override
    public List<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(LocationTarget.class);
    }

}
