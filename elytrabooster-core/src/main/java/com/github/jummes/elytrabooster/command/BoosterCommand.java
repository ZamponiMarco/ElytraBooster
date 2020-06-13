package com.github.jummes.elytrabooster.command;

import com.github.jummes.libs.command.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.Arrays;

public class BoosterCommand extends AbstractCommand {

    protected String boosterString;

    public BoosterCommand(CommandSender sender, String subCommand, String[] arguments,
                          boolean isSenderPlayer) {
        super(sender, subCommand, arguments, isSenderPlayer);
        this.boosterString = subCommand;
    }

    @Override
    protected void execute() {
        if (arguments.length > 0) {
            switch (arguments[0]) {
                case "list":
                    new BoosterListCommand(sender, "list", arguments.length > 1 ? Arrays.copyOfRange(arguments, 1, arguments.length) : new String[0], isSenderPlayer, subCommand).checkExecution();
                case "near":
                    new BoosterNearCommand(sender, "near", arguments.length > 1 ? Arrays.copyOfRange(arguments, 1, arguments.length) : new String[0], isSenderPlayer, subCommand).checkExecution();
                default:
                    new BoosterHelpCommand(sender, "help", arguments.length > 1 ? Arrays.copyOfRange(arguments, 1, arguments.length) : new String[0], isSenderPlayer, subCommand).checkExecution();
            }
        } else {
            new BoosterHelpCommand(sender, "help", new String[0], isSenderPlayer, subCommand).checkExecution();
        }
    }

    @Override
    protected boolean isOnlyPlayer() {
        return false;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("eb.admin.help");
    }
}
