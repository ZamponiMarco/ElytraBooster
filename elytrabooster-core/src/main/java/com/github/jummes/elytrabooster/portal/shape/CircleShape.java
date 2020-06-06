package com.github.jummes.elytrabooster.portal.shape;

import com.github.jummes.elytrabooster.portal.Portal;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SerializableAs("CircleShape")
public class CircleShape extends Shape {

    private static final double AXIS_DISTANCE = 1.0;

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD)
    private double radius;
    @Serializable(headTexture = HEAD)
    private LocationWrapper center;

    /**
     * Construct a new CircleShape from a Portal path
     *
     * @param path path with the portal as root
     */
    public CircleShape(ModelPath<Portal> path) {
        this('x', 5.0, new LocationWrapper(path.getRoot().getShape().getCenterPoint()));
    }

    public CircleShape(char axis, double radius, LocationWrapper center) {
        super(axis);
        this.radius = radius;
        this.center = center;
    }

    public static CircleShape deserialize(Map<String, Object> map) {
        char axis = ((String) map.get("axis")).charAt(0);
        double radius = (double) map.get("radius");
        LocationWrapper center = (LocationWrapper) map.get("center");
        return new CircleShape(axis, radius, center);
    }

    @Override
    public boolean isInPortalArea(Location location, double epsilon) {
        return isInCirclePortalArea(center.getWrapped(), location, epsilon);
    }

    @Override
    public List<Location> buildPoints(boolean isBlockOutline) {
        return getCircle(center.getWrapped(), isBlockOutline);
    }

    private List<Location> getCircle(Location center, boolean isBlockOutline) {
        World world = center.getWorld();
        int amount = isBlockOutline ? 50 * (int) radius : (int) Math.floor(2 * Math.PI * radius);
        double increment = (2 * Math.PI) / amount;

        List<Location> locations = new ArrayList<Location>();
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;

            double newX = axis == 'x' ? center.getX() : center.getX() + (radius * Math.cos(angle));
            double newY = axis == 'y' ? center.getY() : center.getY() + (radius * Math.cos(angle));
            double newZ = axis == 'z' ? center.getZ() : center.getZ() + (radius * Math.sin(angle));
            if (axis == 'z') {
                newY = center.getY() + (radius * Math.sin(angle));
            }

            Location newLocation = isBlockOutline
                    ? world.getBlockAt((int) Math.round(newX), (int) Math.round(newY), (int) Math.round(newZ))
                    .getLocation()
                    : new Location(world, newX, newY, newZ);
            if (!locations.contains(newLocation)) {
                locations.add(newLocation);
            }
        }

        return locations;
    }

    private boolean isInCirclePortalArea(Location center, Location location, double epsilon) {
        Location distance = location.clone().subtract(center);
        double distanceX = Math.abs(distance.getX());
        double distanceY = Math.abs(distance.getY());
        double distanceZ = Math.abs(distance.getZ());

        switch (axis) {
            case 'x':
                return distanceX <= AXIS_DISTANCE && Math.hypot(distanceZ, distanceY) < radius - epsilon;
            case 'y':
                return distanceY <= AXIS_DISTANCE && Math.hypot(distanceX, distanceZ) < radius - epsilon;
            case 'z':
                return distanceZ <= AXIS_DISTANCE && Math.hypot(distanceY, distanceX) < radius - epsilon;
        }
        return false;
    }

    @Override
    public Location getCenterPoint() {
        return center.getWrapped();
    }

}
