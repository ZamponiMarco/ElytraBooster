package com.github.jummes.elytrabooster.commands.boosters;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.manager.BoosterManager;
import com.github.jummes.libs.util.MessageUtils;

public class BoosterCreateCommand extends BoosterCommand {

    public BoosterCreateCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
                                boolean isSenderPlayer, String boosterString) {
        super(plugin, sender, subCommand, arguments, isSenderPlayer, boosterString);
    }

    @Override
    protected void execute() {
        Player player = (Player) sender;
        BoosterManager<?> boosterManager = BoosterManager.getBoosterManager(boosterString);
        if (arguments.length < 1) {
            incorrectUsage();
            return;
        }
        String newBoosterId = arguments[0];

        if (!boosterManager.getBoostersMap().containsKey(newBoosterId)) {
            boosterManager.createDefaultBoosterConfiguration(player, newBoosterId);
            boosterManager.addBooster(newBoosterId);
            player.sendMessage(MessageUtils.color(String.format("&a%s created, &6ID: &a%s", boosterString, newBoosterId)));
        } else {
            invalidBooster();
        }
    }

    @Override
    protected boolean isOnlyPlayer() {
        return true;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("eb.admin.create");
    }

}
