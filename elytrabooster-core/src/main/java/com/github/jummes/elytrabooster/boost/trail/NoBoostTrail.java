package com.github.jummes.elytrabooster.boost.trail;

import com.github.jummes.libs.annotation.Enumerable;
import org.bukkit.entity.Player;

import java.util.Map;

@Enumerable.Child(name = "&c&lNo Trail", description = "gui.boost.trail.none.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
public class NoBoostTrail extends BoostTrail {

    public static NoBoostTrail deserialize(Map<String, Object> map) {
        return new NoBoostTrail();
    }

    @Override
    public void spawnTrail(Player player) {
    }

}
