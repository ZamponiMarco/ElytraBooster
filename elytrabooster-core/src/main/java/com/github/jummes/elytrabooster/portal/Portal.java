package com.github.jummes.elytrabooster.portal;

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
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
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

    private static final String ID_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RkNjM5NzhlODRlMjA5MjI4M2U5Y2QwNmU5ZWY0YmMyMjhiYjlmMjIyMmUxN2VlMzgzYjFjOWQ5N2E4YTAifX19";
    private static final String BOOST_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTNmYzUyMjY0ZDhhZDllNjU0ZjQxNWJlZjAxYTIzOTQ3ZWRiY2NjY2Y2NDkzNzMyODliZWE0ZDE0OTU0MWY3MCJ9fX0=";
    private static final String OUTLINE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQ3OGNjMzkxYWZmYjgwYjJiMzVlYjczNjRmZjc2MmQzODQyNGMwN2U3MjRiOTkzOTZkZWU5MjFmYmJjOWNmIn19fQ==";
    private static final String COOLDOWN_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMyYzYxNzE1MzJhMmE4N2YwZWViMjhlZGQwMTA4MzNmMzNmMGFlNjg0MWE1MjRlMWI1MjAwYTM1ZDM4NTA1MCJ9fX0=";
    private static final String SORTER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzIxNmQxN2RlNDJiZDA5NzY2OWI4ZTA5ZThlNjJkZjhiZjc4MzdkMzk1OTc1NDk2ZTYzNmZkYTRmYTk1ZjNkIn19fQ==========";
    private static final String SHAPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2IyYjVkNDhlNTc1Nzc1NjNhY2EzMTczNTUxOWNiNjIyMjE5YmMwNThiMWYzNDY0OGI2N2I4ZTcxYmMwZmEifX19";
    private static final String PORTAL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBiZmMyNTc3ZjZlMjZjNmM2ZjczNjVjMmM0MDc2YmNjZWU2NTMxMjQ5ODkzODJjZTkzYmNhNGZjOWUzOWIifX19";

    @Serializable(headTexture = ID_HEAD, description = "gui.portal.id")
    private String id;
    @Serializable(headTexture = BOOST_HEAD, description = "gui.portal.boost")
    private SimpleBoost boost;
    @Serializable(headTexture = OUTLINE_HEAD, description = "gui.portal.outlineDescription", recreateTooltip = true)
    private Outline outline;
    @Serializable(headTexture = COOLDOWN_HEAD, description = "gui.portal.cooldown")
    private int cooldown;
    @Serializable(headTexture = SORTER_HEAD, description = "gui.portal.outline.sorter.description", recreateTooltip = true)
    private PointSorter sorter;
    @Serializable(headTexture = SHAPE_HEAD, description = "gui.portal.shapeDescription", recreateTooltip = true)
    private Shape shape;

    private Portal portal;
    private ElytraBooster plugin;

    private int outlineTaskNumber;
    private int checkTaskNumber;
    private int currCooldown;

    @SuppressWarnings("unused")
    public Portal(Player p) {
        this(RandomStringUtils.randomAlphabetic(6),
                new SimpleBoost(new SimpleBoostTrail(), new ArrayList<>(), 30, 3.0, 1.0),
                new BlockPortalOutline(), 0,
                new ClosingPointSorter(),
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
            shape.setPoints(outline instanceof BlockPortalOutline);
            sorter.sort(shape.getPoints(), getShape().getCenterPoint());
            outlineTaskNumber = Bukkit.getServer().getScheduler()
                    .runTaskTimer(plugin, this::drawOutline, 1, plugin.getConfig().getLong("portalOutlineInterval"))
                    .getTaskId();
            checkTaskNumber = plugin.getServer().getScheduler().runTaskTimer(plugin, this::checkPlayersPassing, 0,
                    plugin.getConfig().getLong("portalCheckInterval")).getTaskId();
        }
    }

    public void stopBoosterTask() {
        if (isActive()) {
            outline.eraseOutline(shape.getPoints());
            shape.clearPoints();
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
        PlayerSimpleBoostEvent event = new PlayerSimpleBoostEvent(player);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            boost.boostPlayer(player);
        }
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
        return ItemUtils.getNamedItem(Libs.getWrapper().skullFromValue(PORTAL_HEAD), "&6&lId: &c" + id,
                Libs.getLocale().getList("gui.portal.description"));
    }

    /**
     * When the portal is deleted stop the booster task
     */
    @Override
    public void onRemoval() {
        stopBoosterTask();
    }

    @Override
    public void beforeModify() {
        stopBoosterTask();
    }

    @Override
    public void onModify() {
        runBoosterTask();
    }
}
