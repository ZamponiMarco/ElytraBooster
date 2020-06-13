package com.github.jummes.elytrabooster.boost.trail;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * spawns rainbow trail
 *
 * @author Marco
 */
public class RainbowBoostTrail extends BoostTrail {

    int currentColor;

    public static RainbowBoostTrail deserialize(Map<String, Object> map) {
        return new RainbowBoostTrail();
    }

    @Override
    public void spawnTrail(Player player) {
        currentColor = (currentColor + 1) % 7;
        player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation(), 1,
                new Particle.DustOptions(RainbowColor.values()[currentColor].getColor(), 3));
    }

    private enum RainbowColor {

        VIOLET(148, 0, 211), INDOIGO(75, 0, 130), BLUE(0, 0, 255), GREEN(0, 255, 0), YELLOW(255, 255, 0),
        ORANGE(255, 127, 0), RED(255, 0, 0);

        private final Color color;

        RainbowColor(int r, int g, int b) {
            color = Color.fromRGB(r, g, b);
        }

        public Color getColor() {
            return color;
        }
    }

}
