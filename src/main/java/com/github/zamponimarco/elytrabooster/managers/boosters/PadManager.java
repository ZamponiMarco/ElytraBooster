package com.github.zamponimarco.elytrabooster.managers.boosters;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.boosters.factory.PadFactory;
import com.github.zamponimarco.elytrabooster.boosters.pads.AbstractPad;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

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
		pads = new HashMap<String, AbstractPad>();
		dataYaml.getKeys(false).forEach(id -> addBooster(id));
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
		// TODO Auto-generated method stub

	}

	@Override
	public AbstractPad getBooster(String id) {
		return pads.get(id);
	}

	@Override
	public void addBooster(String id) {
		pads.put(id, PadFactory.buildBooster(plugin, dataYaml.getConfigurationSection(id)));
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
		AbstractPad newPad = getBooster(id);
		return newPad;
	}

	@Override
	public Map<String, AbstractPad> getBoostersMap() {
		return pads;
	}

}
