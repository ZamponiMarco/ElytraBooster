package com.github.jummes.elytrabooster.outline;

import java.util.List;

import org.bukkit.Location;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;

/**
 * Manages the creation of portal outline
 *
 * @author Marco
 */
@Enumerable(classArray = {BlockPortalOutline.class, ParticlePortalOutline.class})
public abstract class Outline implements Model {

    /**
     * Draws the outline
     *
     * @param points
     */
    public abstract void drawOutline(List<Location> points);

    /**
     * Erases the outline
     *
     * @param points
     */
    public abstract void eraseOutline(List<Location> points);

    /**
     * Handle the outline of portals in cooldown
     *
     * @param points
     */
    public abstract void cooldownOutline(List<Location> points, int cooldown, int progress);

    public abstract Object getOutlineType();

    public abstract Object getCooldownType();

}
