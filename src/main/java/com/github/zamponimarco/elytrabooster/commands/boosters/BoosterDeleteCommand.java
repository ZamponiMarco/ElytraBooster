package com.github.zamponimarco.elytrabooster.commands.boosters;

import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class BoosterDeleteCommand extends BoosterCommand {

	public BoosterDeleteCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
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
		booster.stopBoosterTask();
		boosterManager.removeBooster(id);
		boosterManager.getDataYaml().set(id, null);
		boosterManager.saveConfig();
		sender.sendMessage(MessagesUtil.color("&aBooster deleted, &6ID: &a" + id));
	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}

}
