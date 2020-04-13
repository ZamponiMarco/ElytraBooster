package com.github.jummes.elytrabooster.commands.factory;

import org.bukkit.command.CommandSender;

import com.github.jummes.elytrabooster.command.AbstractCommand;
import com.github.jummes.elytrabooster.core.ElytraBooster;

public interface CommandFactory {

    public AbstractCommand buildCommand(ElytraBooster plugin, CommandSender sender, String subCommand,
                                        String[] arguments, boolean isSenderPlayer, String extra);

}
