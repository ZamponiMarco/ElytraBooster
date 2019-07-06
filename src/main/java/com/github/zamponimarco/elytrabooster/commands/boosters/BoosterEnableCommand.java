package com.github.zamponimarco.elytrabooster.commands.boosters;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class BoosterEnableCommand extends BoosterCommand {

	public BoosterEnableCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer, String boosterString) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer, boosterString);
	}

	@Override
	protected void execute() {
		BoosterManager<?> boosterManager = BoosterManager.getBoosterManager(boosterString);
		if (arguments.length < 1) {
			incorrectUsage();
			return;
		}
		String id = arguments[0];
		Booster booster;
		if (boosterManager.getBoostersMap().containsKey(id)) {
			booster = boosterManager.getBooster(id);
		} else {
			invalidBooster();
			return;
		}
		booster.runBoosterTask();
		sender.sendMessage(MessagesUtil.color("&aBooster enabled, &6ID: &a" + id));
	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}
	
	@Override
	protected Permission getPermission() {
		return new Permission("eb.admin.enable");
	}

}
