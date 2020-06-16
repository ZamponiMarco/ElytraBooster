package com.github.jummes.elytrabooster.portal.outline;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;
import org.bukkit.Location;

import java.util.List;

/**
 * Manages the creation of portal outline
 *
 * @author Marco
 */
@Enumerable.Parent(classArray = {BlockPortalOutline.class, ParticlePortalOutline.class, NoOutlinePortal.class})
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
