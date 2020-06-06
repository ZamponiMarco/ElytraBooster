package com.github.jummes.elytrabooster.portal.outline.sorter;

import org.bukkit.Location;

import java.util.Collections;
import java.util.List;

public class RandomPointSorter extends PointSorter {

    @Override
    public void sort(List<Location> points) {
        Collections.shuffle(points);
    }

}
