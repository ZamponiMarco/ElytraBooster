package com.github.jummes.elytrabooster.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.jummes.elytrabooster.boost.Boost;
import com.github.jummes.elytrabooster.boost.SimpleBoost;
import com.github.jummes.elytrabooster.commands.executor.ElytraBoosterCommandExecutor;
import com.github.jummes.elytrabooster.entity.EntityDescription;
import com.github.jummes.elytrabooster.entity.FireworkEntityDescription;
import com.github.jummes.elytrabooster.entity.MushroomEntityDescription;
import com.github.jummes.elytrabooster.entity.PotionEntityDescription;
import com.github.jummes.elytrabooster.entityholder.EntityHolder;
import com.github.jummes.elytrabooster.listener.PlayerGlideListener;
import com.github.jummes.elytrabooster.listener.PlayerSwapHandItemsListener;
import com.github.jummes.elytrabooster.manager.PadManager;
import com.github.jummes.elytrabooster.manager.PortalManager;
import com.github.jummes.elytrabooster.manager.SpawnerManager;
import com.github.jummes.elytrabooster.outline.BlockPortalOutline;
import com.github.jummes.elytrabooster.outline.Outline;
import com.github.jummes.elytrabooster.outline.ParticlePortalOutline;
import com.github.jummes.elytrabooster.portal.Portal;
import com.github.jummes.elytrabooster.shape.CircleShape;
import com.github.jummes.elytrabooster.shape.ComposedShape;
import com.github.jummes.elytrabooster.shape.RectangleShape;
import com.github.jummes.elytrabooster.shape.Shape;
import com.github.jummes.elytrabooster.shape.TriangleShape;
import com.github.jummes.elytrabooster.sorter.ClosingPointSorter;
import com.github.jummes.elytrabooster.sorter.PointSorter;
import com.github.jummes.elytrabooster.spawner.Spawner;
import com.github.jummes.elytrabooster.trail.BoostTrail;
import com.github.jummes.elytrabooster.trail.SimpleBoostTrail;
import com.github.jummes.elytrabooster.volume.SphericVolume;
import com.github.jummes.elytrabooster.volume.Volume;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.localization.PluginLocale;

import lombok.Getter;

@Getter
public class ElytraBooster extends JavaPlugin {

    @Getter
    private static ElytraBooster instance;

    private PortalManager portalManager;
    private SpawnerManager spawnerManager;
    private PadManager padManager;

    private Map<Player, Boolean> statusMap;

    static {
        Libs.registerSerializables();
        ConfigurationSerialization.registerClass(Portal.class);
        ConfigurationSerialization.registerClass(Boost.class);
        ConfigurationSerialization.registerClass(SimpleBoost.class);
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
        ConfigurationSerialization.registerClass(Spawner.class);
        ConfigurationSerialization.registerClass(EntityHolder.class);
        ConfigurationSerialization.registerClass(EntityDescription.class);
        ConfigurationSerialization.registerClass(FireworkEntityDescription.class);
        ConfigurationSerialization.registerClass(MushroomEntityDescription.class);
        ConfigurationSerialization.registerClass(PotionEntityDescription.class);
        ConfigurationSerialization.registerClass(Volume.class);
        ConfigurationSerialization.registerClass(SphericVolume.class);
    }

    public void onEnable() {
        instance = this;
        setUpFolder();
        startupTasks();
    }

    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        portalManager.getPortals().forEach(portal -> portal.stopBoosterTask());
        spawnerManager.getSpawners().forEach(spawner -> spawner.stopBoosterTask());
        padManager.getBoostersMap().values().forEach(pad -> pad.stopBoosterTask());
    }

    private void setUpFolder() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
    }

    private void startupTasks() {
        PluginLocale locale = new PluginLocale(instance, new ArrayList<String>(), "");
        Libs.initializeLibrary(instance, locale);
        new UpdateChecker(this).checkForUpdate();

        portalManager = new PortalManager(Portal.class, "yaml", this);
        spawnerManager = new SpawnerManager(Spawner.class, "yaml", this);
        padManager = new PadManager(this);
        statusMap = new HashMap<Player, Boolean>();
        CommandExecutor executor = new ElytraBoosterCommandExecutor(this);
        getCommand("eb").setExecutor(executor);
        getCommand("eb").setTabCompleter((TabCompleter) executor);
        getServer().getPluginManager().registerEvents(new PlayerGlideListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerSwapHandItemsListener(this), this);
    }
}
