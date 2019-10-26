
package com.github.jummes.elytrabooster.boosters.portals;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.github.jummes.elytrabooster.boosters.portals.utils.PortalUtils;
import com.github.jummes.elytrabooster.boosts.SimpleBoost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.outlines.BlockPortalOutline;
import com.github.jummes.elytrabooster.outlines.PortalOutline;
import com.github.jummes.elytrabooster.outlines.pointsorters.PointSorter;

/**
 * Circle shaped portal class
 * 
 * @author Marco
 *
 */
public class CirclePortal extends AbstractPortal {

	double radius;

	public CirclePortal(ElytraBooster plugin, String id, Location center, char axis, SimpleBoost boost, PortalOutline outline,
			List<UnionPortal> portalsUnion, int cooldown, PointSorter sorter, String measures) {
		super(plugin, id, center, axis, boost, outline, portalsUnion, cooldown, sorter, measures);
		try {
			initMeasures();
			points = getUnionPoints();
			super.runBoosterTask();
		} catch (Exception e) {
			Bukkit.getLogger().warning(warnMessage());
		}
	}

	@Override
	protected void initMeasures() {
		radius = Double.valueOf(measures);
	}

	@Override
	protected List<Location> getPoints() {
		return PortalUtils.getCircle(center, outline instanceof BlockPortalOutline, radius, axis);
	}

	@Override
	protected boolean isInPortalArea(Location location, double epsilon) {
		return PortalUtils.isInCirclePortalArea(location, center, radius, axis, epsilon);
	}

	@Override
	public String getShape() {
		return "circle";
	}

}
