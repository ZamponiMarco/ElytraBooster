package com.github.jummes.elytrabooster.pad;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.jummes.elytrabooster.boost.VerticalBoost;
import com.github.jummes.elytrabooster.booster.Booster;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.manager.BoosterManager;
import com.github.jummes.elytrabooster.visual.PadVisual;

public abstract class AbstractPad implements Booster {

    protected ElytraBooster plugin;
    protected AbstractPad pad;
    protected String id;
    protected Location center;
    protected VerticalBoost boost;
    protected PadVisual visual;
    protected int cooldown;

    protected int currCooldown;
    protected int visualTaskId;

    public AbstractPad(ElytraBooster plugin, String id, Location center, VerticalBoost boost, PadVisual visual,
                       int cooldown) {
        this.plugin = plugin;
        this.id = id;
        this.center = center;
        this.boost = boost;
        this.visual = visual;
        this.cooldown = cooldown;
        this.pad = this;
    }

    public void runBoosterTask() {
        visualTaskId = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> visual.spawnVisual(), 0, 4)
                .getTaskId();
    }

    @Override
    public void stopBoosterTask() {
        plugin.getServer().getScheduler().cancelTask(visualTaskId);
        visual.stopVisual();
    }

    public BoosterManager<?> getDataManager() {
        return plugin.getPadManager();
    }

    public void cooldown() {
        BukkitRunnable cooldownProcess = new BukkitRunnable() {
            @Override
            public void run() {
                if (currCooldown > 0) {
                    currCooldown--;
                } else {
                    pad.runBoosterTask();
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

    public boolean onCooldown() {
        return currCooldown > 0;
    }

    @Override
    public String getId() {
        return id;
    }

    public VerticalBoost getBoost() {
        return boost;
    }

    public PadVisual getVisual() {
        return visual;
    }

    @Override
    public Location getCenter() {
        return center;
    }

    public int getCooldown() {
        return cooldown;
    }

}
