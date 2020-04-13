package com.github.jummes.elytrabooster.sorter;

import java.util.List;

import org.bukkit.Location;

import com.github.jummes.libs.model.Model;

public abstract class PointSorter implements Model {

    public abstract void sort(List<Location> points);

}
