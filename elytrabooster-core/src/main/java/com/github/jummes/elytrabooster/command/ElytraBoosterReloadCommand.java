package com.github.jummes.elytrabooster.command;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.util.MessageUtils;

public class ElytraBoosterReloadCommand extends AbstractCommand {

    public ElytraBoosterReloadCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
                                      boolean isSenderPlayer) {
        super(plugin, sender, subCommand, arguments, isSenderPlayer);
    }

    @Override
    protected void execute() {
        sender.sendMessage(MessageUtils.color("&cReloading &6ElytraBooster"));

//		plugin.getSpawnerManager().getBoostersMap().values().forEach(spawner -> spawner.stopBoosterTask());
        plugin.getPortalManager().getPortals().forEach(portal -> portal.stopBoosterTask());
//		plugin.getPadManager().getBoostersMap().values().forEach(pad -> pad.stopBoosterTask());
        plugin.getServer().getScheduler().cancelTasks(plugin);
//		plugin.getPortalManager().reloadData();
//		plugin.getSpawnerManager().reloadData();
        plugin.getPadManager().reloadData();

        sender.sendMessage(MessageUtils.color("&aReloaded &6ElytraBooster"));
    }

    @Override
    protected boolean isOnlyPlayer() {
        return false;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("eb.admin.reload");
    }

}
