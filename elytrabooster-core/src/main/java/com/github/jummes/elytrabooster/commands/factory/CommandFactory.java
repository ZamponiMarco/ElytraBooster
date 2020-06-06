package com.github.jummes.elytrabooster.commands.factory;

import com.github.jummes.elytrabooster.command.AbstractCommand;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import org.bukkit.command.CommandSender;

public interface CommandFactory {

    public AbstractCommand buildCommand(ElytraBooster plugin, CommandSender sender, String subCommand,
                                        String[] arguments, boolean isSenderPlayer, String extra);

}
