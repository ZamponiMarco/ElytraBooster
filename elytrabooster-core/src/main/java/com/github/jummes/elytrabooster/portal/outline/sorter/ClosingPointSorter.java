package com.github.jummes.elytrabooster.portal.outline.sorter;

import com.github.jummes.libs.annotation.Enumerable;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@SerializableAs("ClosingPointSorter")
@Enumerable.Child(name = "&c&lClosing Sorter", description = "gui.portal.outline.sorter.closing.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2MzMzY2Y2M3YWJlODkzODdlMmZlNmE4MTYxYTE5YmEyMTI1ODg0MjhjODljZWJjOTk1MTk3OWE4MTE2NGVkIn19fQ==")
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
