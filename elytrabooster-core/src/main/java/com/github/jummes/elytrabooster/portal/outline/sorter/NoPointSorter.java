package com.github.jummes.elytrabooster.portal.outline.sorter;

import com.github.jummes.libs.annotation.Enumerable;
import org.bukkit.Location;

import java.util.List;
import java.util.Map;

@Enumerable.Child(name = "&c&lNo sorter",  description = "gui.portal.outline.sorter.none.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
public class NoPointSorter extends PointSorter {
    
    public static NoPointSorter deserialize(Map<String, Object> map) {
        return new NoPointSorter();
    }

    @Override
    public void sort(List<Location> points, Location center) {
    }

}
