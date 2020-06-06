package com.github.jummes.elytrabooster.portal.outline;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

public class NoOutlinePortal extends Outline {

    @Override
    public void drawOutline(List<Location> points) {
    }

    @Override
    public void eraseOutline(List<Location> points) {
    }

    @Override
    public void cooldownOutline(List<Location> points, int cooldown, int progress) {
    }

    @Override
    public Object getOutlineType() {
        return Material.AIR;
    }

    @Override
    public Object getCooldownType() {
        return Material.AIR;
    }

}
