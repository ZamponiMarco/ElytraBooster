package com.github.jummes.elytrabooster.action.targeter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

@AllArgsConstructor
@Getter
public class LocationTarget implements Target {

    private final Location target;
}
