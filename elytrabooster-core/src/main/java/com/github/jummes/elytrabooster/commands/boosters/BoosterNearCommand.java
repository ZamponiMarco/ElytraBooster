package com.github.jummes.elytrabooster.commands.boosters;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import com.github.jummes.elytrabooster.core.ElytraBooster;

public class BoosterNearCommand extends BoosterCommand {

    public BoosterNearCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
                              boolean isSenderPlayer, String boosterString) {
        super(plugin, sender, subCommand, arguments, isSenderPlayer, boosterString);
    }

    @Override
    protected void execute() {
        if (arguments.length >= 1 && StringUtils.isNumeric(arguments[0])) {
        } else {
            return;
        }

        // near command
    }

    @Override
    protected boolean isOnlyPlayer() {
        return true;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("eb.admin.near");
    }

}
