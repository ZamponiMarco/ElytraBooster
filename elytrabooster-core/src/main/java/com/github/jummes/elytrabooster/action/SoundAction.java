package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.LocationTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.annotation.Serializable;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.bukkit.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SoundAction extends AbstractAction {

    private static final String TYPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzkxOWZiZDFkZjQ4YzMyMmMxMzA1YmIxZjhlYWI5Njc0YzIxODQ0YTA0OTNhNTUzNWQ5NGNhYmExZWNhM2MxZCJ9fX0=";
    private static final String CATEGORY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQzYjJlM2U5OTU0ZjgyMmI0M2ZlNWY5MTUwOTllMGE2Y2FhYTgxZjc5MTIyMmI1ODAzZDQxNDVhODUxNzAifX19";
    private static final String PITCH_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQzYjJlM2U5OTU0ZjgyMmI0M2ZlNWY5MTUwOTllMGE2Y2FhYTgxZjc5MTIyMmI1ODAzZDQxNDVhODUxNzAifX19";
    private static final String VOLUME_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGYyMTAwNzM3NGQ0ODBkZTFkNzg1Y2E4Njc2NDE3NTY1MGUwMDc3MzU0MDQyN2FhZWYxYzBjYzE3MGRmM2ExNCJ9fX0=";

    @Serializable(headTexture = TYPE_HEAD, description = "gui.action.sound.type")
    private Sound type;
    @Serializable(headTexture = CATEGORY_HEAD, description = "gui.action.sound.category")
    private SoundCategory category;
    @Serializable(headTexture = PITCH_HEAD, description = "gui.action.sound.pitch")
    private float pitch;
    @Serializable(headTexture = VOLUME_HEAD, description = "gui.action.sound.volume")
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
