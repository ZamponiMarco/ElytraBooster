package com.github.jummes.elytrabooster.portal;

import com.github.jummes.elytrabooster.action.AbstractAction;
import com.github.jummes.elytrabooster.boost.SimpleBoost;
import com.github.jummes.elytrabooster.boost.trail.SimpleBoostTrail;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.event.FinishedCooldownEvent;
import com.github.jummes.elytrabooster.event.PlayerSimpleBoostEvent;
import com.github.jummes.elytrabooster.portal.outline.BlockPortalOutline;
import com.github.jummes.elytrabooster.portal.outline.Outline;
import com.github.jummes.elytrabooster.portal.outline.sorter.ClosingPointSorter;
import com.github.jummes.elytrabooster.portal.outline.sorter.PointSorter;
import com.github.jummes.elytrabooster.portal.shape.CircleShape;
import com.github.jummes.elytrabooster.portal.shape.Shape;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Map;

/**
 * Handle portals data
 */
@Getter
@Setter
@SerializableAs("Portal")
public class Portal implements Model {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD)
    private String id;
    @Serializable(headTexture = HEAD)
    private SimpleBoost boost;
    @Serializable(headTexture = HEAD)
    private Outline outline;
    @Serializable(headTexture = HEAD)
    private int cooldown;
    @Serializable(headTexture = HEAD)
    private PointSorter sorter;
    @Serializable(headTexture = HEAD)
    private Shape shape;

    private Portal portal;
    private ElytraBooster plugin;

    private int outlineTaskNumber;
    private int checkTaskNumber;
    private int currCooldown;

    public Portal(Player p) {
        this(RandomStringUtils.randomAlphabetic(6),
                new SimpleBoost(new SimpleBoostTrail("CRIT"), new ArrayList<AbstractAction>(), 30, 3.0, 1.0),
                new BlockPortalOutline("STONE", "DIRT"), 0,
                new ClosingPointSorter(p.getLocation().getBlock().getLocation()),
                new CircleShape('x', 5, new LocationWrapper(p.getLocation().getBlock().getLocation())));
    }

    public Portal(String id, SimpleBoost boost, Outline outline, int cooldown, PointSorter sorter, Shape shape) {
        this.id = id;
        this.boost = boost;
        this.outline = outline;
        this.cooldown = cooldown;
        this.sorter = sorter;
        this.shape = shape;

        currCooldown = 0;

        this.portal = this;
        this.plugin = ElytraBooster.getInstance();

        runBoosterTask();
    }

    public static Portal deserialize(Map<String, Object> map) {
        String id = (String) map.get("id");
        SimpleBoost boost = (SimpleBoost) map.get("boost");
        Outline outline = (Outline) map.get("outline");
        int cooldown = (int) map.get("cooldown");
        PointSorter sorter = (PointSorter) map.get("sorter");
        Shape shape = (Shape) map.get("shape");
        return new Portal(id, boost, outline, cooldown, sorter, shape);
    }

    public void runBoosterTask() {
        if (!isActive()) {
            outlineTaskNumber = Bukkit.getServer().getScheduler()
                    .runTaskTimer(plugin, () -> drawOutline(), 1, plugin.getConfig().getLong("portalOutlineInterval"))
                    .getTaskId();
            checkTaskNumber = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> checkPlayersPassing(), 0,
                    plugin.getConfig().getLong("portalCheckInterval")).getTaskId();
        }
    }

    public void stopBoosterTask() {
        if (isActive()) {
            outline.eraseOutline(shape.getPoints());
            plugin.getServer().getScheduler().cancelTask(outlineTaskNumber);
            plugin.getServer().getScheduler().cancelTask(checkTaskNumber);
            outlineTaskNumber = 0;
            checkTaskNumber = 0;
        }
    }

    private void checkPlayersPassing() {
        if (!onCooldown()) {
            plugin.getStatusMap().keySet().stream()
                    .filter(player -> !plugin.getStatusMap().get(player) && player.hasPermission("eb.boosters.boost")
                            && player.getWorld().equals(shape.getCenterPoint().getWorld())
                            && shape.isInPortalArea(player.getLocation(), 0))
                    .forEach(this::boostPlayer);
        }
    }

    private void boostPlayer(Player player) {
        Bukkit.getPluginManager().callEvent(new PlayerSimpleBoostEvent(plugin, player, boost));
        if (cooldown > 0)
            cooldown();
    }

    private void drawOutline() {
        if (!onCooldown()) {
            outline.drawOutline(shape.getPoints());
        } else {
            outline.cooldownOutline(shape.getPoints(), cooldown, currCooldown);
        }
    }

    private void cooldown() {
        currCooldown = cooldown;
        BukkitRunnable cooldownProcess = new BukkitRunnable() {
            @Override
            public void run() {
                if (onCooldown()) {
                    currCooldown--;
                } else {
                    Bukkit.getPluginManager().callEvent(new FinishedCooldownEvent(portal));
                    this.cancel();
                }
            }
        };
        cooldownProcess.runTaskTimer(plugin, 0, 1);
    }

    private boolean onCooldown() {
        return currCooldown > 0;
    }

    private boolean isActive() {
        return checkTaskNumber != 0 && outlineTaskNumber != 0;
    }

    @Override
    public ItemStack getGUIItem() {
        return new ItemStack(Material.ACACIA_BOAT);
    }

    /**
     * When the portal is deleted stop the booster task
     */
    @Override
    public void onRemoval() {
        stopBoosterTask();
    }

    /**
     * Before creating a Shape component stop the booster tasks
     */
    @Override
    public void beforeComponentCreation(Class<? extends Model> modelClass) {
        if (ClassUtils.isAssignable(modelClass, Shape.class)) {
            stopBoosterTask();
        }
    }

    /**
     * After setting the new Shape component run the booster task
     */
    @Override
    public void afterComponentSetting(Model model) {
        if (model instanceof Shape) {
            runBoosterTask();
        }
    }

    /**
     * If a setting is modified reload the portal tasks
     */
    @Override
    public void onModify() {
        stopBoosterTask();
        runBoosterTask();
    }

}
