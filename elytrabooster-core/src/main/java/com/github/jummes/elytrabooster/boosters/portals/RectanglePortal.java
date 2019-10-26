package com.github.jummes.elytrabooster.boosters.portals;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.github.jummes.elytrabooster.boosters.portals.utils.PortalUtils;
import com.github.jummes.elytrabooster.boosts.SimpleBoost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.outlines.PortalOutline;
import com.github.jummes.elytrabooster.outlines.pointsorters.PointSorter;

/**
 * Square shaped portal class
 * 
 * @author Marco
 *
 */
public class RectanglePortal extends AbstractPortal {

	double halfLength;
	double halfHeight;

	public RectanglePortal(ElytraBooster plugin, String id, Location center, char axis, SimpleBoost boost, PortalOutline outline,
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
			String[] measuresArray = measures.split(";");
			this.halfLength = Double.valueOf(measuresArray[0]);
			this.halfHeight = Double.valueOf(measuresArray[1]);
	}

	@Override
	protected List<Location> getPoints() {
		return getRectangle();
	}

	@Override
	protected boolean isInPortalArea(Location location, double epsilon) {
		return PortalUtils.isInRectanglePortalArea(center, halfHeight, halfLength, axis, location, epsilon);
	}

	private List<Location> getRectangle() {
		return PortalUtils.getRectangle(center, axis, halfLength, halfHeight);
	}

	@Override
	public String getShape() {
		return halfHeight == halfHeight? "square" : "rectangle";
	}

}
