package com.github.jummes.elytrabooster.pad;

import com.github.jummes.elytrabooster.action.AbstractAction;
import com.github.jummes.elytrabooster.boost.VerticalBoost;
import com.github.jummes.elytrabooster.boost.trail.SimpleBoostTrail;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.pad.visual.FireworkPadVisual;
import com.github.jummes.elytrabooster.pad.visual.PadVisual;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import com.github.jummes.libs.util.ItemUtils;
import com.github.jummes.libs.util.MessageUtils;
import com.google.common.collect.Lists;
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

    private static final String ID_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RkNjM5NzhlODRlMjA5MjI4M2U5Y2QwNmU5ZWY0YmMyMjhiYjlmMjIyMmUxN2VlMzgzYjFjOWQ5N2E4YTAifX19";
    private static final String CENTER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhhMDY1NTg4YzdkYmYxMjk0NTk1YWJhNzc3OTFjM2FkNjVmMTliZjFjOWFkN2E1YzIzZGE0MGI4Mjg2MGI3In19fQ==";
    private static final String BOOST_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTk5YWFmMjQ1NmE2MTIyZGU4ZjZiNjI2ODNmMmJjMmVlZDlhYmI4MWZkNWJlYTFiNGMyM2E1ODE1NmI2NjkifX19";
    private static final String VISUAL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmU2NTUxNmQ4MWFjOTYzZGJjMjQ4NTEzOGRkZGNmOTQzZDdmNzIxMWUzN2VmZWNkNWE1ZmI4ZjVhZDQ5MjAifX19========";
    private static final String COOLDOWN_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMyYzYxNzE1MzJhMmE4N2YwZWViMjhlZGQwMTA4MzNmMzNmMGFlNjg0MWE1MjRlMWI1MjAwYTM1ZDM4NTA1MCJ9fX0=";
    private static final String PAD_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjQ0Yzk3OTgzY2E4YWFmNThjNjk2MTgyOTExZTNiYzY4NTEwZjI5ZDk2MzJkMTM0ZjgzNDU3YTUyZjRlNWY5NSJ9fX0=";


    protected ElytraBooster plugin;
    @Serializable(headTexture = ID_HEAD, description = "gui.pad.id")
    protected String id;
    @Serializable(headTexture = CENTER_HEAD, description = "gui.pad.center")
    protected LocationWrapper center;
    @Serializable(headTexture = BOOST_HEAD, description = "gui.pad.boost")
    protected VerticalBoost boost;
    @Serializable(headTexture = VISUAL_HEAD, description = "gui.pad.visual.description", recreateTooltip = true)
    protected PadVisual visual;
    @Serializable(headTexture = COOLDOWN_HEAD, description = "gui.pad.cooldown")
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
        return ItemUtils.getNamedItem(Libs.getWrapper().skullFromValue(PAD_HEAD), "&6&lId: &c" + id,
                Lists.newArrayList(MessageUtils.color("&6&lLeft click &eto modify."),
                        MessageUtils.color("&6&lRight click &eto delete.")));
    }
}
