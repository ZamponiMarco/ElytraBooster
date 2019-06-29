package com.github.zamponimarco.elytrabooster.commands.factory;

import org.apache.commons.lang.WordUtils;
import org.bukkit.command.CommandSender;

import com.github.zamponimarco.elytrabooster.commands.boosters.BoosterCommand;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class BoosterCommandFactory implements CommandFactory {

	@Override
	public BoosterCommand buildCommand(ElytraBooster plugin, CommandSender sender, String subCommand,
			String[] arguments, boolean isSenderPlayer, String extra) {
		try {
			return (BoosterCommand) Class
					.forName("com.github.zamponimarco.elytrabooster.commands.boosters.Booster"
							+ WordUtils.capitalize(subCommand) + "Command")
					.getConstructor(ElytraBooster.class, CommandSender.class, String.class, String[].class,
							boolean.class, String.class)
					.newInstance(plugin, sender, subCommand, arguments, isSenderPlayer, extra);
		} catch (Exception e) {
		}
		return null;
	}

}
