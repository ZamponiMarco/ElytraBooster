package com.github.zamponimarco.elytrabooster.commands.boosters;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.BoostersListInventoryHolder;
import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;
import com.google.common.collect.Lists;

public class BoosterListCommand extends BoosterCommand {

	public BoosterListCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer, String boosterString) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer, boosterString);
	}

	@Override
	protected void execute() {
		Player player = (Player) sender;
		List<Booster> boosters = Lists
				.newArrayList(BoosterManager.getBoosterManager(boosterString).getBoostersMap().values());
		boosters.sort((p1, p2) -> (int) (p1.getCenter().distance(player.getLocation())
				- p2.getCenter().distance(player.getLocation())));

		player.openInventory(new BoostersListInventoryHolder(plugin, MessagesUtil.color("&1&lBooster list"),
				boosterString, boosters, 1).getInventory());
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
