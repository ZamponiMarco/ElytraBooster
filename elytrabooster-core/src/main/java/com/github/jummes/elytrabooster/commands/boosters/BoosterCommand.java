package com.github.jummes.elytrabooster.commands.boosters;

import org.bukkit.command.CommandSender;

import com.github.jummes.elytrabooster.command.AbstractCommand;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.util.MessageUtils;

public abstract class BoosterCommand extends AbstractCommand {

    protected String boosterString;

    public BoosterCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
                          boolean isSenderPlayer, String boosterString) {
        super(plugin, sender, subCommand, arguments, isSenderPlayer);
        this.boosterString = boosterString;
    }

    protected void invalidBooster() {
        sender.sendMessage((MessageUtils.color("&cBooster passed in input is invalid")));
    }

}
