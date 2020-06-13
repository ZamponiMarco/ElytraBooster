package com.github.jummes.elytrabooster.portal.outline.sorter;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@SerializableAs("ClosingPointSorter")
public class ClosingPointSorter extends PointSorter {

    private Location center;

    private final Comparator<Location> locationComparator = new Comparator<Location>() {
        @Override
        public int compare(Location p1, Location p2) {
            double d1 = Math.abs(p1.clone().toVector().dot(center.toVector()));
            double d2 = Math.abs(p2.clone().toVector().dot(center.toVector()));
            return (int) (d1 - d2);
        }
    };
    
    public static ClosingPointSorter deserialize(Map<String, Object> map) {
        return new ClosingPointSorter();
    }

    @Override
    public void sort(List<Location> points, Location center) {
        this.center = center;
        points.sort(locationComparator);
    }

}
