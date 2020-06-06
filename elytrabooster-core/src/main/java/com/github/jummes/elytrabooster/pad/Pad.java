package com.github.jummes.elytrabooster.pad;

import com.github.jummes.elytrabooster.action.AbstractAction;
import com.github.jummes.elytrabooster.boost.VerticalBoost;
import com.github.jummes.elytrabooster.boost.trail.SimpleBoostTrail;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.pad.visual.FireworkPadVisual;
import com.github.jummes.elytrabooster.pad.visual.PadVisual;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Map;

public class Pad implements Model {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    protected ElytraBooster plugin;
    @Serializable(headTexture = HEAD)
    protected String id;
    @Serializable(headTexture = HEAD)
    protected LocationWrapper center;
    @Serializable(headTexture = HEAD)
    protected VerticalBoost boost;
    @Serializable(headTexture = HEAD)
    protected PadVisual visual;
    @Serializable(headTexture = HEAD)
    protected int cooldown;

    protected int currCooldown;

    public Pad(Player p) {
        this(RandomStringUtils.randomAlphabetic(6), new LocationWrapper(p.getLocation().getBlock().getLocation()), new VerticalBoost(new SimpleBoostTrail(), new ArrayList<AbstractAction>(), 2.0, 0.5), new FireworkPadVisual(), 0);
    }

    public Pad(String id, LocationWrapper center, VerticalBoost boost, PadVisual visual,
               int cooldown) {
        this.plugin = ElytraBooster.getInstance();
        this.id = id;
        this.center = center;
        this.boost = boost;
        this.visual = visual;
        this.cooldown = cooldown;
        runBoosterTask();
    }

    public static Pad deserialize(Map<String, Object> map) {
        String id = (String) map.get("id");
        LocationWrapper center = (LocationWrapper) map.get("center");
        VerticalBoost boost = (VerticalBoost) map.get("boost");
        PadVisual visual = (PadVisual) map.get("visual");
        int cooldown = (int) map.get("cooldown");
        return new Pad(id, center, boost, visual, cooldown);
    }

    public void runBoosterTask() {
        visual.startVisual(center.getWrapped());
    }

    public void stopBoosterTask() {
        visual.stopVisual();
    }

    public void cooldown() {
        BukkitRunnable cooldownProcess = new BukkitRunnable() {
            @Override
            public void run() {
                if (onCooldown()) {
                    currCooldown--;
                } else {
                    runBoosterTask();
                    this.cancel();
                }
            }
        };
        if (cooldown > 0) {
            this.stopBoosterTask();
            currCooldown = cooldown;
            cooldownProcess.runTaskTimer(plugin, 0, 1);
        }
    }

    @Override
    public void onRemoval() {
        stopBoosterTask();
    }

    @Override
    public void beforeComponentCreation(Class<? extends Model> modelClass) {
        if (ClassUtils.isAssignable(modelClass, PadVisual.class)) {
            stopBoosterTask();
        }
    }

    @Override
    public void afterComponentSetting(Model model) {
        if (model instanceof PadVisual) {
            runBoosterTask();
        }
    }

    public boolean onCooldown() {
        return currCooldown > 0;
    }

    public String getId() {
        return id;
    }

    public VerticalBoost getBoost() {
        return boost;
    }

    public PadVisual getVisual() {
        return visual;
    }

    public Location getCenter() {
        return center.getWrapped();
    }

    @Override
    public ItemStack getGUIItem() {
        return new ItemStack(Material.ELYTRA);
    }
}
