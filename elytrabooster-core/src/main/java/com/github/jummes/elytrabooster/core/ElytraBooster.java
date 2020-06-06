package com.github.jummes.elytrabooster.core;

import com.github.jummes.elytrabooster.boost.Boost;
import com.github.jummes.elytrabooster.boost.SimpleBoost;
import com.github.jummes.elytrabooster.boost.VerticalBoost;
import com.github.jummes.elytrabooster.boost.trail.*;
import com.github.jummes.elytrabooster.command.BoosterCommand;
import com.github.jummes.elytrabooster.command.ElytraBoosterHelpCommand;
import com.github.jummes.elytrabooster.command.ElytraBoosterReloadCommand;
import com.github.jummes.elytrabooster.listener.PlayerGlideListener;
import com.github.jummes.elytrabooster.listener.PlayerSwapHandItemsListener;
import com.github.jummes.elytrabooster.manager.PadManager;
import com.github.jummes.elytrabooster.manager.PortalManager;
import com.github.jummes.elytrabooster.manager.SpawnerManager;
import com.github.jummes.elytrabooster.pad.Pad;
import com.github.jummes.elytrabooster.pad.visual.FireworkPadVisual;
import com.github.jummes.elytrabooster.pad.visual.FlamePadVisual;
import com.github.jummes.elytrabooster.pad.visual.PadVisual;
import com.github.jummes.elytrabooster.portal.Portal;
import com.github.jummes.elytrabooster.portal.outline.BlockPortalOutline;
import com.github.jummes.elytrabooster.portal.outline.Outline;
import com.github.jummes.elytrabooster.portal.outline.ParticlePortalOutline;
import com.github.jummes.elytrabooster.portal.outline.sorter.ClosingPointSorter;
import com.github.jummes.elytrabooster.portal.outline.sorter.PointSorter;
import com.github.jummes.elytrabooster.portal.shape.*;
import com.github.jummes.elytrabooster.spawner.Spawner;
import com.github.jummes.elytrabooster.spawner.entityholder.EntityHolder;
import com.github.jummes.elytrabooster.spawner.entityholder.entity.EntityDescription;
import com.github.jummes.elytrabooster.spawner.entityholder.entity.FireworkEntityDescription;
import com.github.jummes.elytrabooster.spawner.entityholder.entity.MushroomEntityDescription;
import com.github.jummes.elytrabooster.spawner.entityholder.entity.PotionEntityDescription;
import com.github.jummes.elytrabooster.spawner.volume.SphericVolume;
import com.github.jummes.elytrabooster.spawner.volume.Volume;
import com.github.jummes.libs.command.PluginCommandExecutor;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.localization.PluginLocale;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ElytraBooster extends JavaPlugin {

    private static ElytraBooster instance;

    static {
        Libs.registerSerializables();

        // Boost
        ConfigurationSerialization.registerClass(Boost.class);
        ConfigurationSerialization.registerClass(SimpleBoost.class);
        ConfigurationSerialization.registerClass(VerticalBoost.class);
        ConfigurationSerialization.registerClass(BoostTrail.class);
        ConfigurationSerialization.registerClass(SimpleBoostTrail.class);
        ConfigurationSerialization.registerClass(HelixBoostTrail.class);
        ConfigurationSerialization.registerClass(NoBoostTrail.class);
        ConfigurationSerialization.registerClass(RainbowBoostTrail.class);

        // Portal
        ConfigurationSerialization.registerClass(Portal.class);
        ConfigurationSerialization.registerClass(BoostTrail.class);
        ConfigurationSerialization.registerClass(SimpleBoostTrail.class);
        ConfigurationSerialization.registerClass(Outline.class);
        ConfigurationSerialization.registerClass(BlockPortalOutline.class);
        ConfigurationSerialization.registerClass(ParticlePortalOutline.class);
        ConfigurationSerialization.registerClass(PointSorter.class);
        ConfigurationSerialization.registerClass(ClosingPointSorter.class);
        ConfigurationSerialization.registerClass(Shape.class);
        ConfigurationSerialization.registerClass(CircleShape.class);
        ConfigurationSerialization.registerClass(RectangleShape.class);
        ConfigurationSerialization.registerClass(TriangleShape.class);
        ConfigurationSerialization.registerClass(ComposedShape.class);
        ConfigurationSerialization.registerClass(ComposedShape.SingleShape.class);
        ConfigurationSerialization.registerClass(TriangleShape.Vector2D.class);

        // Spawner
        ConfigurationSerialization.registerClass(Spawner.class);
        ConfigurationSerialization.registerClass(EntityHolder.class);
        ConfigurationSerialization.registerClass(EntityDescription.class);
        ConfigurationSerialization.registerClass(FireworkEntityDescription.class);
        ConfigurationSerialization.registerClass(MushroomEntityDescription.class);
        ConfigurationSerialization.registerClass(PotionEntityDescription.class);
        ConfigurationSerialization.registerClass(Volume.class);
        ConfigurationSerialization.registerClass(SphericVolume.class);

        // Pads
        ConfigurationSerialization.registerClass(Pad.class);
        ConfigurationSerialization.registerClass(PadVisual.class);
        ConfigurationSerialization.registerClass(FireworkPadVisual.class);
        ConfigurationSerialization.registerClass(FlamePadVisual.class);
    }

    private PortalManager portalManager;
    private SpawnerManager spawnerManager;
    private PadManager padManager;
    private Map<Player, Boolean> statusMap;

    public static ElytraBooster getInstance() {
        return ElytraBooster.instance;
    }

    public void onEnable() {
        instance = this;
        setUpFolder();
        startupTasks();
    }

    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        portalManager.getPortals().forEach(Portal::stopBoosterTask);
        spawnerManager.getSpawners().forEach(Spawner::stopBoosterTask);
        padManager.getPads().forEach(Pad::stopBoosterTask);
    }

    private void setUpFolder() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
    }

    private void startupTasks() {
        PluginLocale locale = new PluginLocale(instance, new ArrayList<>(), "");
        Libs.initializeLibrary(instance, locale);
        new UpdateChecker(this).checkForUpdate();

        portalManager = new PortalManager(Portal.class, "yaml", this);
        spawnerManager = new SpawnerManager(Spawner.class, "yaml", this);
        padManager = new PadManager(Pad.class, "yaml", this);
        statusMap = new HashMap<>();
        PluginCommandExecutor ex = new PluginCommandExecutor(ElytraBoosterHelpCommand.class, "help");
        ex.registerCommand("reload", ElytraBoosterReloadCommand.class);
        ex.registerCommand("spawner", BoosterCommand.class);
        ex.registerCommand("portal", BoosterCommand.class);
        ex.registerCommand("pad", BoosterCommand.class);
        getCommand("eb").setExecutor(ex);
        getCommand("eb").setTabCompleter((TabCompleter) ex);
        getServer().getPluginManager().registerEvents(new PlayerGlideListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerSwapHandItemsListener(), this);
    }

    public PortalManager getPortalManager() {
        return this.portalManager;
    }

    public SpawnerManager getSpawnerManager() {
        return this.spawnerManager;
    }

    public PadManager getPadManager() {
        return this.padManager;
    }

    public Map<Player, Boolean> getStatusMap() {
        return this.statusMap;
    }
}
