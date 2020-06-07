package com.github.jummes.elytrabooster.portal.outline.sorter;

import org.bukkit.Location;

import java.util.List;
import java.util.Map;

public class NoPointSorter extends PointSorter {
    
    public static NoPointSorter deserialize(Map<String, Object> map) {
        return new NoPointSorter();
    }

    @Override
    public void sort(List<Location> points, Location center) {
    }

}
