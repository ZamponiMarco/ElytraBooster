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

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD)
    private LocationWrapper center;
    @Serializable(headTexture = HEAD)
    private double minRadius;
    @Serializable(headTexture = HEAD)
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
        return loc;
    }

}
