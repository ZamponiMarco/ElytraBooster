package com.github.jummes.elytrabooster.spawner.volume;

import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
public class SphericVolume extends Volume {

    private static final String CENTER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhhMDY1NTg4YzdkYmYxMjk0NTk1YWJhNzc3OTFjM2FkNjVmMTliZjFjOWFkN2E1YzIzZGE0MGI4Mjg2MGI3In19fQ==";
    private static final String MIN_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWQ2YjEyOTNkYjcyOWQwMTBmNTM0Y2UxMzYxYmJjNTVhZTVhOGM4ZjgzYTE5NDdhZmU3YTg2NzMyZWZjMiJ9fX0=";
    private static final String MAX_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTI0MjMwMmViZDY1NWY2ZDQyOWMxZTRhZWRlMjFiN2Y1YzRkYjY4YTQwNDVlYmFlYzE3NjMzYTA1MGExYTEifX19";

    @Serializable(headTexture = CENTER_HEAD, description = "gui.spawner.volume.spheric.center")
    private LocationWrapper center;
    @Serializable(headTexture = MIN_HEAD, description = "gui.spawner.volume.spheric.minRadius")
    private double minRadius;
    @Serializable(headTexture = MAX_HEAD, description = "gui.spawner.volume.spheric.maxRadius")
    private double maxRadius;

    public SphericVolume(Player p) {
        this(new LocationWrapper(p.getLocation().getBlock().getLocation()), 1.0, 3.0);
    }

    public static SphericVolume deserialize(Map<String, Object> map) {
        LocationWrapper center = (LocationWrapper) map.get("center");
        double minRadius = (double) map.get("minRadius");
        double maxRadius = (double) map.get("maxRadius");
        return new SphericVolume(center, minRadius, maxRadius);
    }

    @Override
    public Location getRandomPoint() {
        Random r = new Random();
        Vector v = new Vector();
        Location center = this.center.getWrapped();
        Location loc;
        do {
            double randomX = r.nextDouble() * 2 - 1;
            v.setX(randomX);
            double randomY = r.nextDouble() * 2 - 1;
            v.setY(randomY);
            double randomZ = r.nextDouble() * 2 - 1;
            v.setZ(randomZ);
            v.normalize();
            double radius = ((1 - minRadius / maxRadius) * r.nextDouble() + minRadius / maxRadius) * maxRadius;
            v.multiply(radius);
            loc = center.clone().add(v);
        } while (loc.getBlock().getType() != Material.AIR);
        // TODO stop if no points available
        return loc;
    }

    @Override
    public Location getCenterPoint() {
        return center.getWrapped();
    }

}
