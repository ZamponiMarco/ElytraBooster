package com.github.jummes.elytrabooster.spawner.volume;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;
import org.bukkit.Location;

@Enumerable.Parent(classArray = {SphericVolume.class})
public abstract class Volume implements Model {

    public abstract Location getRandomPoint();

    public abstract Location getCenterPoint();

}
