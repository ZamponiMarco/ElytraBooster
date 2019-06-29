package com.github.zamponimarco.elytrabooster.commands.boosters;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.BoostersListInventoryHolder;
import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;
import com.github.zamponimarco.elytrabooster.utils.MessagesUtil;

public class BoosterNearCommand extends BoosterCommand {

	public BoosterNearCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer, String boosterString) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer, boosterString);
	}

	@Override
	protected void execute() {
		Player player = (Player) sender;
		int range;
		if (arguments.length >= 1 && StringUtils.isNumeric(arguments[0])) {
			range = Integer.valueOf(arguments[0]);
		} else {
			return;
		}

		List<Booster> boosters = BoosterManager.getBoosterManager(boosterString).getBoostersMap().values().stream().filter(portal -> {
			return portal.getCenter().distance(player.getLocation()) <= range;
		}).collect(Collectors.toList());
		boosters.sort((p1, p2) -> (int) (p1.getCenter().distance(player.getLocation())
				- p2.getCenter().distance(player.getLocation())));

		player.openInventory(new BoostersListInventoryHolder(plugin, MessagesUtil.color(String.format("&1&lBoosters within %d blocks", range)),
				boosterString, boosters, 1).getInventory());
	}

	@Override
	protected boolean isOnlyPlayer() {
		return true;
	}

}
