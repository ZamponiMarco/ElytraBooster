package com.github.jummes.elytrabooster.boost;

import com.github.jummes.elytrabooster.action.AbstractAction;
import com.github.jummes.elytrabooster.action.targeter.LocationTarget;
import com.github.jummes.elytrabooster.action.targeter.PlayerTarget;
import com.github.jummes.elytrabooster.boost.trail.BoostTrail;
import com.github.jummes.elytrabooster.boost.trail.SimpleBoostTrail;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.util.MessageUtils;
import com.google.common.collect.Lists;
import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;

@Getter
@SerializableAs("SimpleBoost")
@Enumerable.Child(name = "&c&lSimple Boost", description = "gui.boost.simple.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTNmYzUyMjY0ZDhhZDllNjU0ZjQxNWJlZjAxYTIzOTQ3ZWRiY2NjY2Y2NDkzNzMyODliZWE0ZDE0OTU0MWY3MCJ9fX0=")
public class SimpleBoost extends Boost {

    private static final String INITIAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJjZGM2Zjg4Yzg1M2U4MzE0OTVhMTc0NmViMjdhYTYxYjlkYWMyZTg2YTQ0Yjk1MjJlM2UyYjdkYzUifX19=";
    private static final String FINAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI5MjBjMzgxNWI5YzQ1OTJlNjQwOGUzMjIzZjMxMzUxZmM1NzhmMzU1OTFiYzdmOWJlYmQyMWVmZDhhMDk3In19fQ===";
    private static final String BOOST_DURATION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Q1MWM4M2NjMWViY2E1YTFiNmU2Nzk0N2UyMGI0YTJhNmM5ZWZlYTBjZjQ2OTI5NDQ4ZTBlMzc0MTZkNTgzMyJ9fX0====";

    @Serializable(headTexture = BOOST_DURATION_HEAD, description = "gui.boost.simple.duration")
    private final int boostDuration;
    @Serializable(headTexture = INITIAL_VELOCITY_HEAD, description = "gui.boost.simple.initial")
    private final double initialVelocity;
    @Serializable(headTexture = FINAL_VELOCITY_HEAD, description = "gui.boost.simple.final")
    private final double finalVelocity;

    public SimpleBoost() {
        this(new SimpleBoostTrail(Particle.FIREWORKS_SPARK), Lists.newArrayList(), 30, 3.0, 1.0);
    }

    public SimpleBoost(BoostTrail trail, List<AbstractAction> boostActions, int boostDuration, double initialVelocity,
                       double finalVelocity) {
        super(trail, boostActions);
        this.boostDuration = boostDuration;
        this.initialVelocity = initialVelocity;
        this.finalVelocity = finalVelocity;
    }

    @SuppressWarnings("unchecked")
    public static SimpleBoost deserialize(Map<String, Object> map) {
        BoostTrail trail = (BoostTrail) map.get("trail");
        List<AbstractAction> boostActions = (List<AbstractAction>) map.get("boostActions");
        int boostDuration = (int) map.get("boostDuration");
        double initialVelocity = (double) map.get("initialVelocity");
        double finalVelocity = (double) map.get("finalVelocity");
        return new SimpleBoost(trail, boostActions, boostDuration, initialVelocity, finalVelocity);
    }

    @Override
    public void boostPlayer(Player player) {
        ElytraBooster.getInstance().getStatusMap().replace(player, true);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 20, 1);
        boostActions.forEach(abstractAction -> abstractAction.executeAction(new PlayerTarget(player)));
        boostActions.forEach(abstractAction -> abstractAction.executeAction(new LocationTarget(player.getLocation())));
        getBoostProcess(player).runTaskTimer(ElytraBooster.getInstance(), 0, 1);
    }


    private BukkitRunnable getBoostProcess(Player player) {
        return new BukkitRunnable() {

            final double d = Math.pow((finalVelocity / initialVelocity),
                    (1.0 / boostDuration));
            double tempVelocity = initialVelocity;
            int counter = 0;

            @Override
            public void run() {

                if (counter == boostDuration) {
                    ElytraBooster.getInstance().getStatusMap().replace(player, false);
                    this.cancel();
                } else if (!player.isGliding()) {
                    ElytraBooster.getInstance().getStatusMap().remove(player);
                    this.cancel();
                }

                sendProgressMessage(player, counter);
                trail.spawnTrail(player);
                player.setVelocity(player.getLocation().getDirection().normalize().multiply(tempVelocity));

                counter++;
                tempVelocity *= d;
            }
        };
    }


    /**
     * Sends to the player the progress bar in the action bar
     *
     * @param player
     * @param counter
     */
    private void sendProgressMessage(Player player, int counter) {
        int progress = (int) Math.floor((counter / (double) boostDuration) * 30);

        StringBuilder sb = new StringBuilder();
        sb.append("&a");
        sb.append(repeat(30 - progress, "|"));
        sb.append("&c");
        sb.append(repeat(progress, "|"));

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                new ComponentBuilder(
                        MessageUtils.color(MessageUtils.color(String.format("&2Boost &6[%s&6]", sb.toString()))))
                        .create());
    }

    private String repeat(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }
}
