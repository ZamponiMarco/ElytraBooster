package com.github.jummes.elytrabooster.portal.outline.sorter;

import java.util.Collections;
import java.util.List;

import org.bukkit.Location;

public class RandomPointSorter extends PointSorter {

    @Override
    public void sort(List<Location> points) {
        Collections.shuffle(points);
    }

}
