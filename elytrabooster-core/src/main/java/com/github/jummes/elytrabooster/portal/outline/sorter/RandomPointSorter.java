package com.github.jummes.elytrabooster.portal.outline.sorter;

import org.bukkit.Location;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RandomPointSorter extends PointSorter {
    
    public static RandomPointSorter deserialize(Map<String, Object> map) {
        return new RandomPointSorter();
    }

    @Override
    public void sort(List<Location> points, Location center) {
        Collections.shuffle(points);
    }

}
