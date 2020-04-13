package com.github.jummes.elytrabooster.command;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.util.MessageUtils;

public abstract class AbstractCommand {

    protected static final String NO_PERMISSION = MessageUtils.color("&cYou don't have the permission");
    protected static final String WRONG_SENDER = MessageUtils.color("&cThis command can be used only by a player");
    protected static final String INCORRECT_USAGE = MessageUtils.color("&cIncorrect command syntax, type /eb help");

    protected ElytraBooster plugin;
    protected CommandSender sender;
    protected String subCommand;
    protected String[] arguments;
    protected boolean isSenderPlayer;

    public AbstractCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
                           boolean isSenderPlayer) {
        this.plugin = plugin;
        this.sender = sender;
        this.subCommand = subCommand;
        this.arguments = arguments;
        this.isSenderPlayer = isSenderPlayer;
    }

    protected boolean canSenderTypeExecute() {
        return !isOnlyPlayer() || isSenderPlayer;
    }

    protected boolean hasPermission() {
        return sender.hasPermission(getPermission());
    }

    public void checkExecution() {
        if (!canSenderTypeExecute()) {
            sender.sendMessage(WRONG_SENDER);
            return;
        }

        if (!hasPermission()) {
            sender.sendMessage(NO_PERMISSION);
            return;
        }

        execute();
    }

    protected void incorrectUsage() {
        sender.sendMessage(INCORRECT_USAGE);
    }

    protected abstract void execute();

    protected abstract boolean isOnlyPlayer();

    protected abstract Permission getPermission();

}
