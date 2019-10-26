package com.github.jummes.elytrabooster.commands.boosters;

import org.apache.commons.lang.WordUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.gui.BoostersListInventoryHolder;
import com.github.jummes.elytrabooster.utils.MessageUtils;

public class BoosterListCommand extends BoosterCommand {

	private static final String MENU_TITLE = MessageUtils.color("&1%ss list (&3%s&1)");

	public BoosterListCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer, String boosterString) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer, boosterString);
	}

	@Override
	protected void execute() {
		Player player = (Player) sender;

		player.openInventory(
				new BoostersListInventoryHolder(
						String.format(MENU_TITLE, WordUtils.capitalize(boosterString), player.getWorld().getName()), boosterString, 1,
				player.getLocation()).getInventory());
	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

	@Override
	protected Permission getPermission() {
		return new Permission("eb.admin.list");
	}

}
