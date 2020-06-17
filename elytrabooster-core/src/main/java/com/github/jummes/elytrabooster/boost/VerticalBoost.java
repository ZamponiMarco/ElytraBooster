package com.github.jummes.elytrabooster.boost;

import com.github.jummes.elytrabooster.action.AbstractAction;
import com.github.jummes.elytrabooster.action.targeter.LocationTarget;
import com.github.jummes.elytrabooster.action.targeter.PlayerTarget;
import com.github.jummes.elytrabooster.boost.trail.BoostTrail;
import com.github.jummes.elytrabooster.boost.trail.SimpleBoostTrail;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.annotation.Serializable;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class VerticalBoost extends Boost {

    private static final String HORIZONTAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ4NjVhYWUyNzQ2YTliOGU5YTRmZTYyOWZiMDhkMThkMGE5MjUxZTVjY2JlNWZhNzA1MWY1M2VhYjliOTQifX19=";
    private static final String VERTICAL_VELOCITY_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTUxNDlkZGRhZGVkMjBkMjQ0ZTBiYjYyYTJkOWZhMGRjNmM2YTc4NjI1NTkzMjhhOTRmNzc3MjVmNTNjMzU4In19fQ=====";

    @Serializable(headTexture = VERTICAL_VELOCITY_HEAD, description = "gui.boost.vertical.vertical")
    private double verticalVelocity;
    @Serializable(headTexture = HORIZONTAL_VELOCITY_HEAD, description = "gui.boost.vertical.horizontal")
    private double horizontalVelocity;

    public VerticalBoost(){
        this(new SimpleBoostTrail(), new ArrayList<>(), 2.0, 0.5);
    }

    public VerticalBoost(BoostTrail trail, List<AbstractAction> boostActions, double verticalVelocity, double horizontalVelocity) {
        super(trail, boostActions);
        this.verticalVelocity = verticalVelocity;
        this.horizontalVelocity = horizontalVelocity;
    }

    @SuppressWarnings("unchecked")
    public static VerticalBoost deserialize(Map<String, Object> map) {
        BoostTrail trail = (BoostTrail) map.get("trail");
        List<AbstractAction> boostActions = (List<AbstractAction>) map.get("boostActions");
        double verticalVelocity = (double) map.get("verticalVelocity");
        double horizontalVelocity = (double) map.get("horizontalVelocity");
        return new VerticalBoost(trail, boostActions, verticalVelocity, horizontalVelocity);
    }

    @Override
    public void boostPlayer(Player player) {
        player.setVelocity(player.getLocation().getDirection().setY(0).multiply(horizontalVelocity)
                .add(new Vector(0, verticalVelocity, 0)));
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 20, 1);
        boostActions.forEach(abstractAction -> abstractAction.executeAction(new PlayerTarget(player)));
        boostActions.forEach(abstractAction -> abstractAction.executeAction(new LocationTarget(player.getLocation())));
        getOpenElytraProcess(player).runTaskTimer(ElytraBooster.getInstance(), 0, 1);
    }


    private BukkitRunnable getOpenElytraProcess(Player player) {
        return new BukkitRunnable() {

            @Override
            public void run() {
                trail.spawnTrail(player);
                if (player.getVelocity().normalize().getY() < 0) {
                    player.setGliding(true);
                    ElytraBooster.getInstance().getStatusMap().put(player, false);
                    this.cancel();
                } else if (player.getFallDistance() > 1 && player.isOnGround()) {
                    this.cancel();
                }
            }
        };

    }
}
