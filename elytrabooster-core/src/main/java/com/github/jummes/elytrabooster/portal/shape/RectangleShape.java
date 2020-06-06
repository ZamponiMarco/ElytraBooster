package com.github.jummes.elytrabooster.portal.shape;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;

import com.github.jummes.elytrabooster.portal.Portal;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.model.wrapper.LocationWrapper;

public class RectangleShape extends Shape {

    private static final double AXIS_DISTANCE = 1.0;

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD)
    private LocationWrapper center;
    @Serializable(headTexture = HEAD)
    private double halfWidth;
    @Serializable(headTexture = HEAD)
    private double halfHeight;

    /**
     * Construct a new RectangleShape from a Portal path
     *
     * @param path path with the portal as root
     */
    public RectangleShape(ModelPath<Portal> path) {
        this('x', new LocationWrapper(path.getRoot().getShape().getCenterPoint()), 5.0, 5.0);
    }

    public RectangleShape(char axis, LocationWrapper center, double halfWidth, double halfHeight) {
        super(axis);
        this.center = center;
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
        setPoints();
    }

    @Override
    public boolean isInPortalArea(Location location, double epsilon) {
        return isInRectanglePortalArea(center.getWrapped(), halfHeight, halfWidth, axis, location, epsilon);
    }

    @Override
    protected List<Location> buildPoints() {
        return getRectangle(center.getWrapped(), axis, halfWidth, halfHeight);
    }

    private List<Location> getRectangle(Location center, char axis, double halfWidth, double halfHeight) {
        List<Location> locations = new ArrayList<Location>();
        getSquareLines(center, axis, halfWidth, halfHeight).forEach(line -> locations.addAll(splitLine(line)));
        return locations;
    }

    private boolean isInRectanglePortalArea(Location center, double halfHeight, double halfWidth, char axis,
                                            Location location, double epsilon) {
        Location distance = location.clone().subtract(center);
        double distanceX = Math.abs(distance.getX());
        double distanceY = Math.abs(distance.getY());
        double distanceZ = Math.abs(distance.getZ());

        boolean isInX = axis == 'x' ? distanceX <= AXIS_DISTANCE : distanceX < halfWidth - epsilon;
        boolean isInY = axis == 'y' ? distanceY <= AXIS_DISTANCE : distanceY < halfWidth - epsilon;
        isInY = axis == 'z' ? distanceY < halfHeight - epsilon : isInY;
        boolean isInZ = axis == 'z' ? distanceZ <= AXIS_DISTANCE : distanceZ < halfHeight - epsilon;

        return isInX && isInY && isInZ;
    }

    private Set<Location[]> getSquareLines(Location center, char axis, double halfWidth, double halfHeight) {
        Set<Location[]> linesSet = new HashSet<Location[]>();
        Location point1 = null;
        Location point2 = null;
        Location point3 = null;
        Location point4 = null;
        switch (axis) {
            case 'x':
                point1 = center.clone().add(0, -halfWidth, -halfHeight);
                point2 = center.clone().add(0, halfWidth, -halfHeight);
                point3 = center.clone().add(0, halfWidth, halfHeight);
                point4 = center.clone().add(0, -halfWidth, halfHeight);
                break;
            case 'y':
                point1 = center.clone().add(-halfWidth, 0, -halfHeight);
                point2 = center.clone().add(halfWidth, 0, -halfHeight);
                point3 = center.clone().add(halfWidth, 0, halfHeight);
                point4 = center.clone().add(-halfWidth, 0, halfHeight);
                break;
            case 'z':
                point1 = center.clone().add(-halfWidth, -halfHeight, 0);
                point2 = center.clone().add(halfWidth, -halfHeight, 0);
                point3 = center.clone().add(halfWidth, halfHeight, 0);
                point4 = center.clone().add(-halfWidth, halfHeight, 0);
                break;
        }
        linesSet.add(new Location[]{point4, point3});
        linesSet.add(new Location[]{point3, point2});
        linesSet.add(new Location[]{point2, point1});
        linesSet.add(new Location[]{point1, point4});
        return linesSet;
    }

    private List<Location> splitLine(Location[] line) {
        int amount = (int) line[0].distance(line[1]);
        Location decrement = line[0].clone().subtract(line[1]).multiply(1.0 / amount);
        List<Location> linePoints = new ArrayList<Location>();
        Location loc = line[0].clone();
        for (int i = 0; i < amount; i++) {
            linePoints.add(loc);
            loc = loc.subtract(decrement).clone();
        }

        return linePoints;
    }

    public static RectangleShape deserialize(Map<String, Object> map) {
        char axis = ((String) map.get("axis")).charAt(0);
        LocationWrapper center = (LocationWrapper) map.get("center");
        double halfWidth = (double) map.get("halfWidth");
        double halfHeight = (double) map.get("halfHeight");
        return new RectangleShape(axis, center, halfWidth, halfHeight);
    }

    @Override
    public Location getCenterPoint() {
        return center.getWrapped();
    }

}
