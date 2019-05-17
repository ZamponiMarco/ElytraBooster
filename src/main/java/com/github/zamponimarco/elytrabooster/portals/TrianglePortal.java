package com.github.zamponimarco.elytrabooster.portals;

import java.util.List;

import org.bukkit.Location;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.outline.PortalOutline;
import com.github.zamponimarco.elytrabooster.portals.utils.PortalUtils;
import com.github.zamponimarco.elytrabooster.trails.BoostTrail;

public class TrianglePortal extends AbstractPortal{

	private Location point2;
	private Location point3;
	
	public TrianglePortal(ElytraBooster plugin, String id, Location point1, char axis,
			double initialVelocity, double finalVelocity, int boostDuration, PortalOutline outline,
			List<UnionPortal> portalsUnion, BoostTrail trail, int cooldown, String measures) {
		super(plugin, id, point1, axis, initialVelocity, finalVelocity, boostDuration, outline, portalsUnion, trail, cooldown);
		initMeasures(measures);
		
		super.runPortalTask();
	}

	private void initMeasures(String measures) {
		String[] measuresArray = measures.split(";");
		switch(axis) {
		case 'x':
			point2 = new Location(center.getWorld(), center.getX(), Double.valueOf(measuresArray[0]), Double.valueOf(measuresArray[1]));
			point3 = new Location(center.getWorld(), center.getX(), Double.valueOf(measuresArray[2]), Double.valueOf(measuresArray[3]));
			break;
		case 'y':
			point2 = new Location(center.getWorld(), Double.valueOf(measuresArray[0]),center.getY(), Double.valueOf(measuresArray[1]));
			point3 = new Location(center.getWorld(), Double.valueOf(measuresArray[2]),center.getY(), Double.valueOf(measuresArray[3]));
			break;
		case 'z':
			point2 = new Location(center.getWorld(), Double.valueOf(measuresArray[0]), Double.valueOf(measuresArray[1]),center.getZ());
			point3 = new Location(center.getWorld(), Double.valueOf(measuresArray[2]), Double.valueOf(measuresArray[3]),center.getZ());
			break;
		}
	}

	@Override
	protected List<Location> getPoints() {
		return PortalUtils.getTriangle(center, point2, point3, axis);
	}

	@Override
	protected boolean isInPortalArea(Location location, double epsilon) {
		return PortalUtils.isInTrianglePortalArea(location, center, point2, point3, axis, epsilon);
	}

}
