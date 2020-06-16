package com.github.jummes.elytrabooster.portal.outline.sorter;

import com.github.jummes.libs.annotation.Enumerable;
import org.bukkit.Location;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Enumerable.Child(name = "&c&lRandom Sorter", description = "gui.portal.outline.sorter.random.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzM5ODg2YTkxNDgxOTM2NDg1OTgzNzAyNGM0YmNmYTg1M2Q4NmJkODJiZTU0MTdkNjhjMDU3Yjg0MzMifX19")
public class RandomPointSorter extends PointSorter {
    
    public static RandomPointSorter deserialize(Map<String, Object> map) {
        return new RandomPointSorter();
    }

    @Override
    public void sort(List<Location> points, Location center) {
        Collections.shuffle(points);
    }

}
