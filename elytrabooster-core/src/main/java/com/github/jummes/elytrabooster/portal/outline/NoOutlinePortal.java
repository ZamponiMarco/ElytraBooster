package com.github.jummes.elytrabooster.portal.outline;

import com.github.jummes.libs.annotation.Enumerable;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lNo outline", description = "gui.portal.outline.none.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==")
public class NoOutlinePortal extends Outline {

    public static NoOutlinePortal deserialize(Map<String, Object> map) {
        return new NoOutlinePortal();
    }

    @Override
    public void drawOutline(List<Location> points) {
    }

    @Override
    public void eraseOutline(List<Location> points) {
    }

    @Override
    public void cooldownOutline(List<Location> points, int cooldown, int progress) {
    }

    @Override
    public Object getOutlineType() {
        return Material.AIR;
    }

    @Override
    public Object getCooldownType() {
        return Material.AIR;
    }

}
