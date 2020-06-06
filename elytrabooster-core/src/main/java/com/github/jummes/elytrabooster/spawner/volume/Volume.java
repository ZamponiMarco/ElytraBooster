package com.github.jummes.elytrabooster.spawner.volume;

import org.bukkit.Location;

import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.model.Model;

@Enumerable(classArray = {SphericVolume.class})
public abstract class Volume implements Model {

    public abstract Location getRandomPoint();

}
