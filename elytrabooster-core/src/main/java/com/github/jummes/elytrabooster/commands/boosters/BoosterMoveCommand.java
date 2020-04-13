package com.github.jummes.elytrabooster.commands.boosters;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import com.github.jummes.elytrabooster.core.ElytraBooster;

public class BoosterMoveCommand extends BoosterCommand {

    public BoosterMoveCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
                              boolean isSenderPlayer, String boosterString) {
        super(plugin, sender, subCommand, arguments, isSenderPlayer, boosterString);
    }

    @Override
    protected void execute() {
//		Player player = (Player) sender;
//
//		BoosterManager<?> boosterManager = BoosterManager.getBoosterManager(boosterString);
//		if (arguments.length < 1) {
//			incorrectUsage();
//			return;
//		}
//		String id = arguments[0];
//		Booster booster = boosterManager.getBooster(id);
//		if (booster == null) {
//			invalidBooster();
//			return;
//		}
//
//		ConfigurationSection section = (ConfigurationSection) boosterManager.getDataYaml().get(id);
//		World world = player.getLocation().getWorld();
//		Double x = arguments.length == 4 ? Double.valueOf(arguments[1]) : player.getLocation().getBlockX();
//		Double y = arguments.length == 4 ? Double.valueOf(arguments[2]) : player.getLocation().getBlockY();
//		Double z = arguments.length == 4 ? Double.valueOf(arguments[3]) : player.getLocation().getBlockZ();
//		section.set("world", world.getName());
//		section.set("x", x);
//		section.set("y", y);
//		section.set("z", z);
//
//		if (booster instanceof Portal && !((Portal) booster).getPortalsUnion().isEmpty()) {
//			Location oldLocation = booster.getCenter();
//			Location newLocation = new Location(world, x, y, z);
//			Vector movement = newLocation.clone().subtract(oldLocation.clone()).toVector();
//			List<String> portalsUnion = new ArrayList<String>();
//			((Portal) booster).getPortalsUnion().forEach(unionPortal -> {
//				unionPortal.setCenter(unionPortal.getCenter().clone().add(movement));
//				portalsUnion.add(unionPortal.toString());
//			});
//			section.set("portalsUnion", portalsUnion);
//		}
//		booster.stopBoosterTask();
//		boosterManager.saveConfig();
//		boosterManager.addBooster(id);
    }

    @Override
    protected boolean isOnlyPlayer() {
        return true;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("eb.admin.move");
    }

}
