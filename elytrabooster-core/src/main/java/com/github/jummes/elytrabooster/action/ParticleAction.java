package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

import java.util.Map;

public class ParticleAction extends AbstractAction {

    private Location location;
    private Particle type;
    private int count;
    private double offset;
    private double speed;
    private boolean force;

    public ParticleAction(ElytraBooster plugin, Map<String, String> parameters) {
        super(plugin, parameters);
    }

    @Override
    protected void parseParameters(Map<String, String> parameters) {
        World world = Bukkit.getWorld(parameters.get("world"));
        double x = Double.parseDouble(parameters.get("x"));
        double y = Double.parseDouble(parameters.get("y"));
        double z = Double.parseDouble(parameters.get("z"));
        location = new Location(world, x, y, z);

        type = Particle.valueOf(parameters.getOrDefault("type", "FLAME").toUpperCase());

        count = Integer.parseInt(parameters.getOrDefault("count", "1"));

        offset = Double.parseDouble(parameters.getOrDefault("offset", "0"));

        speed = Double.parseDouble(parameters.getOrDefault("speed", "0"));

        force = Boolean.parseBoolean(parameters.getOrDefault("force", "false"));
    }

    @Override
    public void executeAction() {
        location.getWorld().spawnParticle(type, location, count, offset, offset, offset, speed, null, force);
    }

}
