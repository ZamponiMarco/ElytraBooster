package com.github.jummes.elytrabooster.portal.outline.sorter;

import com.github.jummes.libs.model.Model;
import org.bukkit.Location;

import java.util.List;

public abstract class PointSorter implements Model {

    public abstract void sort(List<Location> points);

}
