package com.github.zamponimarco.elytrabooster.managers;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Handles plugin data
 * 
 * @author Marco
 *
 */
public interface DataManager {

	/**
	 * Loads the data file and sets it in an instance variable
	 */
	public void loadDataFile();

	/**
	 * Loads the yaml configuration, sets it in an instance variable and initializes
	 * data structures
	 */
	public void loadDataYaml();

	/**
	 * Loads the data from the yaml configurations and puts it in his data structure
	 */
	public void loadData();
	
	/**
	 * Gets the yaml configuration
	 * 
	 * @return
	 */
	public YamlConfiguration getDataYaml();

	/**
	 * Saves the configuration file
	 * 
	 */
	public void saveConfig();

	public default void reloadData() {
		loadDataFile();
		loadDataYaml();
		loadData();
	}

}
