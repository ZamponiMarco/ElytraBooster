package com.github.jummes.elytrabooster.commands.boosters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.utils.MessageUtils;

public class BoosterHelpCommand extends BoosterCommand {

	public BoosterHelpCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer, String boosterString) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer, boosterString);
	}

	private List<String> pages;

	@Override
	protected void execute() {
		pages = new ArrayList<String>();
		setUpPages();
		int numberOfPages = 1;

		int pageToPrint;
		if (arguments.length >= 1 && StringUtils.isNumeric(arguments[0])
				&& Integer.valueOf(arguments[0]) > numberOfPages) {
			pageToPrint = Integer.valueOf(arguments[0]) - 1;
		} else {
			pageToPrint = 0;
		}

		sender.sendMessage(pages.get(pageToPrint));
	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}
	
	@Override
	protected Permission getPermission() {
		return new Permission("eb.admin.help");
	}

	private void setUpPages() {
		StringBuilder page = new StringBuilder();
		page.append(MessageUtils.header("ElytraBooster Help"));
		page.append(MessageUtils.color(String.format("&2/eb booster help &c[page] &7Print the boosters help message.\n"
				+ "&2/eb booster list &7List boosters.\n" + "&2/eb booster create &c[id] &7Create a new booster.\n"
				+ "&2/eb booster delete &c[id] &7Deletes the given booster.\n"
				+ "&2/eb booster move &c[id] <x> <y> <z> &7Move the booster to your location or to the given coords, if present.\n"
				+ "&2/eb booster set &c[id] [param:value,...] &7Sets the params to the values given in input.\n"
				+ "&2/eb booster near &c[radius] &7List all the boosters within [radius] blocks.\n"
				+ "&2/eb booster disable &c[id] &7Disable the booster named [id]\n"
				+ "&2/eb booster enable &c[id] &7Enable the booster named [id]\n"))
				.replaceAll("booster", boosterString));
		page.append(MessageUtils.footer(1, 1));
		pages.add(page.toString());
	}

}
