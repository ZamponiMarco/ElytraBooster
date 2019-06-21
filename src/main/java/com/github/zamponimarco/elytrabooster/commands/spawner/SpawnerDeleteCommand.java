package com.github.zamponimarco.elytrabooster.commands.spawner;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.SpawnerManager;
import com.github.zamponimarco.elytrabooster.spawners.AbstractSpawner;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class SpawnerDeleteCommand extends SpawnerCommand {

	public SpawnerDeleteCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		SpawnerManager spawnerManager = plugin.getSpawnerManager();
		if (arguments.length < 1) {
			incorrectUsage();
			return;
		}
		String id = arguments[0];

		AbstractSpawner spawner;
		if (spawnerManager.getSpawnersMap().containsKey(id)) {
			spawner = spawnerManager.getSpawner(id);
		} else {
			invalidSpawner();
			return;
		}
		spawner.stopSpawnerTask();
		spawnerManager.removeSpawner(id);
		spawnerManager.getDataYaml().set(id, null);
		spawnerManager.saveConfig();
		sender.sendMessage(MessagesUtil.color("&aSpawner deleted, &6ID: &a" + id));
	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
