package com.github.zamponimarco.elytrabooster.commands;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class ElytraBoosterReloadCommand extends AbstractCommand {

	public ElytraBoosterReloadCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer);
	}

	@Override
	protected void execute() {
		sender.sendMessage(MessagesUtil.color("&cReloading &6ElytraBooster"));
		
		plugin.getSpawnerManager().getBoostersMap().values().forEach(spawner -> spawner.stopBoosterTask());
		plugin.getPortalManager().getBoostersMap().values().forEach(portal -> portal.stopBoosterTask());
		plugin.getPadManager().getBoostersMap().values().forEach(pad -> pad.stopBoosterTask());
		plugin.getServer().getScheduler().cancelTasks(plugin);
		plugin.getSettingsManager().reloadData();
		plugin.getPortalManager().reloadData();
		plugin.getSpawnerManager().reloadData();
		plugin.getPadManager().reloadData();
		
		sender.sendMessage(MessagesUtil.color("&aReloaded &6ElytraBooster"));
	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
