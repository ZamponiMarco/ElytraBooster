package com.github.jummes.elytrabooster.commands.boosters;

import com.github.jummes.elytrabooster.booster.Booster;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.manager.BoosterManager;
import com.github.jummes.libs.util.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.Arrays;

public class BoosterSetCommand extends BoosterCommand {

    public BoosterSetCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
                             boolean isSenderPlayer, String boosterString) {
        super(plugin, sender, subCommand, arguments, isSenderPlayer, boosterString);
    }

    @Override
    protected void execute() {
        BoosterManager<?> boosterManager = BoosterManager.getBoosterManager(boosterString);
        if (arguments.length < 2) {
            incorrectUsage();
            return;
        }
        String id = arguments[0];
        Booster booster = boosterManager.getBooster(id);

        if (booster == null) {
            invalidBooster();
            return;
        }

        try {
            Arrays.asList(arguments[1].split(",")).forEach(string -> {
                String[] argument = string.split(":");
                boosterManager.setParam(id, argument[0], argument[1]);
                sender.sendMessage(MessageUtils
                        .color("&aPortal modified, &6ID: &a" + id + ", &6" + argument[0] + ": &a" + argument[1]));
            });
        } catch (Exception e) {
            incorrectUsage();
            return;
        }

        boosterManager.reloadBooster(booster);
    }

    @Override
    protected boolean isOnlyPlayer() {
        return false;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("eb.admin.set");
    }

}
