package com.github.zamponimarco.elytrabooster.managers.boosters;

import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.managers.DataManager;

public interface BoosterManager<T extends Booster> extends DataManager {

	/**
	 * Creates a default yaml configuration section for a certain booster
	 * 
	 * @param creator
	 * @param id
	 * @return the created configuration section
	 */
	public ConfigurationSection createDefaultBoosterConfiguration(Player creator, String id);

	/**
	 * Sets the value of a parameter inside a configuration section to a certain
	 * value
	 * 
	 * @param id
	 * @param param
	 * @param value
	 */
	public void setParam(String id, String param, String value);

	/**
	 * Gets the booster named as the name given in input
	 * 
	 * @param id
	 * @return a booster
	 */
	public T getBooster(String id);

	/**
	 * Builds and adds a booster to the list of active boosters
	 * 
	 * @param id
	 */
	public void addBooster(String id);

	/**
	 * Removes a booster from boosters list
	 * 
	 * @param id
	 */
	public void removeBooster(String id);

	/**
	 * Reloads the booster with the new config files values and returns the new
	 * booster
	 * 
	 * @param booster
	 * @return new booster
	 */
	public T reloadBooster(Booster booster);
	
	/**
	 * Returns the map of boosters
	 * 
	 * @return map of boosters
	 */
	public Map<String, T> getBoostersMap();
}
