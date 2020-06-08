package com.github.jummes.elytrabooster.portal.shape;

import com.github.jummes.elytrabooster.portal.Portal;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import lombok.Getter;
import org.bukkit.Location;

import java.util.*;

public class TriangleShape extends Shape {

    private static final double AXIS_DISTANCE = 1.0;

    private static final String CENTER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhhMDY1NTg4YzdkYmYxMjk0NTk1YWJhNzc3OTFjM2FkNjVmMTliZjFjOWFkN2E1YzIzZGE0MGI4Mjg2MGI3In19fQ==";

    @Serializable(headTexture = CENTER_HEAD)
    private LocationWrapper pointOne;
    @Serializable(headTexture = CENTER_HEAD)
    private Vector2D pointTwo;
    @Serializable(headTexture = CENTER_HEAD)
    private Vector2D pointThree;

    public TriangleShape(ModelPath<Portal> path) {
        this(path.getRoot().getShape().getAxis(), new LocationWrapper(path.getRoot().getShape().getCenterPoint()),
                new Vector2D(5, 5), new Vector2D(-5, 5));
    }

    public TriangleShape(char axis, LocationWrapper pointOne, Vector2D pointTwo, Vector2D pointThree) {
        super(axis);
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;
        this.pointThree = pointThree;
    }

    public static TriangleShape deserialize(Map<String, Object> map) {
        char axis = ((String) map.get("axis")).charAt(0);
        LocationWrapper pointOne = (LocationWrapper) map.get("pointOne");
        Vector2D pointTwo = (Vector2D) map.get("pointTwo");
        Vector2D pointThree = (Vector2D) map.get("pointThree");
        return new TriangleShape(axis, pointOne, pointTwo, pointThree);
    }

    @Override
    public boolean isInPortalArea(Location location, double epsilon) {
        return isInTrianglePortalArea(location, pointOne.getWrapped(), sumLocationToVector(pointOne, pointTwo),
                sumLocationToVector(pointOne, pointThree), axis, epsilon);
    }

    @Override
    protected List<Location> buildPoints(boolean isBlockOutline) {
        return getTriangle(pointOne.getWrapped(), sumLocationToVector(pointOne, pointTwo),
                sumLocationToVector(pointOne, pointThree));
    }

    @Override
    public Location getCenterPoint() {
        return pointOne.getWrapped().clone().add(sumLocationToVector(pointOne, pointTwo).clone())
                .add(sumLocationToVector(pointOne, pointThree).clone()).multiply(1 / 3.0);
    }

    private Location sumLocationToVector(LocationWrapper point, Vector2D vector) {
        Location loc = point.getWrapped().clone();
        switch (axis) {
            case 'x':
                return loc.add(0, vector.incrementOne, vector.incrementTwo);
            case 'y':
                return loc.add(vector.incrementOne, 0, vector.incrementTwo);
            case 'z':
                return loc.add(vector.incrementOne, vector.incrementTwo, 0);
        }
        return null;
    }

    private List<Location> getTriangle(Location point1, Location point2, Location point3) {
        List<Location> locations = new ArrayList<Location>();
        getTriangleLines(point1, point2, point3).forEach(line -> locations.addAll(splitLine(line)));
        return locations;
    }

    private boolean isInTrianglePortalArea(Location location, Location point1, Location point2, Location point3,
                                           char axis, double epsilon) {
        double axisDistance = 0;
        switch (axis) {
            case 'x':
                axisDistance = Math.abs((location.getX() - point1.getX()));
                break;
            case 'y':
                axisDistance = Math.abs((location.getY() - point1.getY()));
                break;
            case 'z':
                axisDistance = Math.abs((location.getZ() - point1.getZ()));
                break;
        }

        boolean d12 = dotProduct(location, point1, point2, axis, epsilon);
        boolean d23 = dotProduct(location, point2, point3, axis, epsilon);
        boolean d31 = dotProduct(location, point3, point1, axis, epsilon);
        boolean isInArea = (d12 && d23 && d31) || (!d12 && !d23 && !d31);
        return isInArea && axisDistance <= AXIS_DISTANCE;
    }

    private boolean dotProduct(Location p1, Location p2, Location p3, char axis, double epsilon) {
        double x1 = axis == 'x' ? p1.getZ() : p1.getX();
        double x2 = axis == 'x' ? p2.getZ() : p2.getX();
        double x3 = axis == 'x' ? p3.getZ() : p3.getX();
        double y1 = axis == 'y' ? p1.getX() : p1.getY();
        double y2 = axis == 'y' ? p2.getX() : p2.getY();
        double y3 = axis == 'y' ? p3.getX() : p3.getY();
        if (axis == 'y') {
            y1 = p1.getZ();
            y2 = p2.getZ();
            y3 = p3.getZ();
        }

        return (x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3) >= -epsilon;
    }

    private Set<Location[]> getTriangleLines(Location point1, Location point2, Location point3) {
        Set<Location[]> linesSet = new HashSet<Location[]>();
        linesSet.add(new Location[]{point3, point2});
        linesSet.add(new Location[]{point2, point1});
        linesSet.add(new Location[]{point1, point3});
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

    @Getter
    public static class Vector2D implements Model {
        private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

        @Serializable(headTexture = HEAD)
        private double incrementOne;
        @Serializable(headTexture = HEAD)
        private double incrementTwo;

        public Vector2D(double incrementOne, double incrementTwo) {
            this.incrementOne = incrementOne;
            this.incrementTwo = incrementTwo;
        }

        public static Vector2D deserialize(Map<String, Object> map) {
            double x = (double) map.get("incrementOne");
            double y = (double) map.get("incrementTwo");
            return new Vector2D(x, y);
        }
    }

}
