package com.github.jummes.elytrabooster.commands.factory;

import com.github.jummes.elytrabooster.command.AbstractCommand;
import com.github.jummes.elytrabooster.command.ElytraBoosterHelpCommand;
import com.github.jummes.elytrabooster.command.ElytraBoosterReloadCommand;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import org.bukkit.command.CommandSender;

public class ElytraBoosterCommandFactory implements CommandFactory {

    @Override
    public AbstractCommand buildCommand(ElytraBooster plugin, CommandSender sender, String subCommand,
                                        String[] arguments, boolean isSenderPlayer, String extra) {
        switch (subCommand) {
            case "reload":
                return new ElytraBoosterReloadCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
            default:
                return new ElytraBoosterHelpCommand(plugin, sender, subCommand, arguments, isSenderPlayer);
        }
    }

}
