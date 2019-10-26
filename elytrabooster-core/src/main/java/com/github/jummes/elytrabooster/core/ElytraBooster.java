package com.github.jummes.elytrabooster.core;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.jummes.elytrabooster.commands.executor.ElytraBoosterCommandExecutor;
import com.github.jummes.elytrabooster.listeners.InventoryClickListener;
import com.github.jummes.elytrabooster.listeners.PlayerChatListener;
import com.github.jummes.elytrabooster.listeners.PlayerGlideListener;
import com.github.jummes.elytrabooster.listeners.PlayerSwapHandItemsListener;
import com.github.jummes.elytrabooster.managers.SettingsManager;
import com.github.jummes.elytrabooster.managers.boosters.PadManager;
import com.github.jummes.elytrabooster.managers.boosters.PortalManager;
import com.github.jummes.elytrabooster.managers.boosters.SpawnerManager;
import com.github.jummes.elytrabooster.settings.Settings;
import com.github.jummes.elytrabooster.wrapper.VersionWrapper;

public class ElytraBooster extends JavaPlugin {

	private static ElytraBooster instance;
	private VersionWrapper wrapper;

	private SettingsManager settingsManager;
	private PortalManager portalManager;
	private SpawnerManager spawnerManager;
	private PadManager padManager;

	private Map<Player, Boolean> statusMap;

	public void onEnable() {
		instance = this;
		setUpFolder();
		setUpWrapper();
		startupTasks();
		getLogger().info("Enabled ElytraBooster v" + getDescription().getVersion());
	}

	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
		spawnerManager.getBoostersMap().values().forEach(spawner -> spawner.stopBoosterTask());
		padManager.getBoostersMap().values().forEach(pad -> pad.stopBoosterTask());
	}

	private void setUpFolder() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
	}
	
	private void setUpWrapper() {
		String serverVersion = getServer().getClass().getPackage().getName();
		String version = serverVersion.substring(serverVersion.lastIndexOf('.') + 1);

		try {
			wrapper = (VersionWrapper) Class
					.forName("com.github.jummes.elytrabooster.wrapper.VersionWrapper_" + version).getConstructor()
					.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startupTasks() {
		new UpdateChecker(this).checkForUpdate();

		settingsManager = new SettingsManager(this);
		portalManager = new PortalManager(this);
		spawnerManager = new SpawnerManager(this);
		padManager = new PadManager(this);
		if (Boolean.valueOf(settingsManager.getSetting(Settings.METRICS))) {
			new Metrics(this);
		}
		statusMap = new HashMap<Player, Boolean>();
		CommandExecutor executor = new ElytraBoosterCommandExecutor(this);
		getCommand("eb").setExecutor(executor);
		getCommand("eb").setTabCompleter((TabCompleter) executor);
		getServer().getPluginManager().registerEvents(new PlayerGlideListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerSwapHandItemsListener(this), this);
	}

	public PortalManager getPortalManager() {
		return portalManager;
	}

	public Map<Player, Boolean> getStatusMap() {
		return statusMap;
	}

	public SettingsManager getSettingsManager() {
		return settingsManager;
	}

	public SpawnerManager getSpawnerManager() {
		return spawnerManager;
	}

	public PadManager getPadManager() {
		return padManager;
	}

	public static ElytraBooster getInstance() {
		return instance;
	}

	public VersionWrapper getWrapper() {
		return wrapper;
	}

}
