package com.github.jummes.elytrabooster.action;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;

import com.github.jummes.elytrabooster.core.ElytraBooster;

public class SoundAction extends AbstractAction {

    private Location location;
    private Sound type;
    private SoundCategory category;
    private float pitch;
    private float volume;

    public SoundAction(ElytraBooster plugin, Map<String, String> parameters) {
        super(plugin, parameters);
    }

    @Override
    protected void parseParameters(Map<String, String> parameters) {
        World world = Bukkit.getWorld(parameters.get("world"));
        double x = Double.valueOf(parameters.get("x"));
        double y = Double.valueOf(parameters.get("y"));
        double z = Double.valueOf(parameters.get("z"));
        location = new Location(world, x, y, z);

        type = Sound.valueOf(parameters.getOrDefault("type", "block_anvil_break").toUpperCase());

        category = SoundCategory.valueOf(parameters.getOrDefault("category", "master").toUpperCase());

        pitch = Float.valueOf(parameters.getOrDefault("pitch", "1.0"));

        volume = Float.valueOf(parameters.getOrDefault("volume", "20"));
    }

    @Override
    public void executeAction() {
        location.getWorld().playSound(location, type, category, volume, pitch);
    }

}
