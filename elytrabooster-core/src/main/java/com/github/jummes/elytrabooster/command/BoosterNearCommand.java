package com.github.jummes.elytrabooster.command;

import com.github.jummes.libs.command.AbstractCommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class BoosterNearCommand extends AbstractCommand {

    private String boosterString;

    public BoosterNearCommand(CommandSender sender, String subCommand, String[] arguments,
                              boolean isSenderPlayer, String boosterString) {
        super(sender, subCommand, arguments, isSenderPlayer);
        this.boosterString = boosterString;
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
