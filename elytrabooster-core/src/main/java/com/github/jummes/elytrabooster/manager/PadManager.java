package com.github.jummes.elytrabooster.manager;

import com.github.jummes.elytrabooster.booster.Booster;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.pad.AbstractPad;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PadManager implements BoosterManager<AbstractPad> {

    private final static String FILENAME = "pads.yml";

    private ElytraBooster plugin;

    private File dataFile;
    private YamlConfiguration dataYaml;
    private Map<String, AbstractPad> pads;

    public PadManager(ElytraBooster plugin) {
        super();
        this.plugin = plugin;

        loadDataFile();
        loadDataYaml();
        loadData();
    }

    @Override
    public void loadDataFile() {
        dataFile = new File(plugin.getDataFolder(), FILENAME);
        if (!dataFile.exists()) {
            plugin.saveResource(FILENAME, false);
        }
    }

    @Override
    public void loadDataYaml() {
        dataYaml = YamlConfiguration.loadConfiguration(dataFile);
    }

    @Override
    public void loadData() {
        pads = new HashMap<>();
        dataYaml.getKeys(false).forEach(this::addBooster);
    }

    @Override
    public YamlConfiguration getDataYaml() {
        return dataYaml;
    }

    @Override
    public void saveConfig() {
        try {
            dataYaml.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ConfigurationSection createDefaultBoosterConfiguration(Player creator, String id) {
        ConfigurationSection newPad = dataYaml.createSection(id);
        newPad.set("world", creator.getWorld().getName());
        newPad.set("x", creator.getLocation().getBlockX());
        newPad.set("y", creator.getLocation().getBlockY());
        newPad.set("z", creator.getLocation().getBlockZ());
        saveConfig();
        return newPad;
    }

    @Override
    public void setParam(String id, String param, String value) {
        ConfigurationSection portal = getDataYaml().getConfigurationSection(id);
        switch (param) {
            case "verticalVelocity":
            case "horizontalVelocity":
                portal.set(param, Double.valueOf(value));
                break;
            case "cooldown":
                portal.set(param, Integer.valueOf(value));
                break;
            case "trail":
            case "visual":
                portal.set(param, value);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public AbstractPad getBooster(String id) {
        return pads.get(id);
    }

    @Override
    public void addBooster(String id) {
//		pads.put(id, PadFactory.buildBooster(plugin, dataYaml.getConfigurationSection(id)));
    }

    @Override
    public void removeBooster(String id) {
        pads.remove(id);
    }

    @Override
    public AbstractPad reloadBooster(Booster booster) {
        saveConfig();
        booster.stopBoosterTask();
        String id = booster.getId();
        removeBooster(id);
        addBooster(id);
        return getBooster(id);
    }

    @Override
    public Map<String, AbstractPad> getBoostersMap() {
        return pads;
    }

}
