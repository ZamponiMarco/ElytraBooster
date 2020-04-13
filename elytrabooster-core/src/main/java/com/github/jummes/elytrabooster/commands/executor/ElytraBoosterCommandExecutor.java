package com.github.jummes.elytrabooster.commands.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.github.jummes.elytrabooster.commands.factory.BoosterCommandFactory;
import com.github.jummes.elytrabooster.commands.factory.CommandFactory;
import com.github.jummes.elytrabooster.commands.factory.ElytraBoosterCommandFactory;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.google.common.collect.Lists;

public class ElytraBoosterCommandExecutor implements CommandExecutor, TabCompleter {

    private static final List<String> SUBTYPES = Lists.newArrayList("portal", "spawner", "help", "reload", "pad");
    private static final List<String> BOOSTER_SUBCOMMANDS = Lists.newArrayList("create", "delete", "disable", "enable",
            "help", "move", "near", "list", "set");

    private ElytraBooster plugin;

    public ElytraBoosterCommandExecutor(ElytraBooster plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("eb")) {

            boolean isSenderPlayer = sender instanceof Player;
            String subType = args.length >= 1 ? args[0] : "";
            String subCommand = "";
            String[] arguments = new String[0];
            String extra = "";

            CommandFactory commandFactory = null;
            switch (subType) {
                case "portal":
                    commandFactory = new BoosterCommandFactory();
                    subCommand = args.length >= 2 ? args[1] : "";
                    arguments = args.length >= 3 ? Arrays.copyOfRange(args, 2, args.length) : new String[0];
                    extra = subType;
                    break;
                case "spawner":
                    commandFactory = new BoosterCommandFactory();
                    subCommand = args.length >= 2 ? args[1] : "";
                    arguments = args.length >= 3 ? Arrays.copyOfRange(args, 2, args.length) : new String[0];
                    extra = subType;
                    break;
                case "pad":
                    commandFactory = new BoosterCommandFactory();
                    subCommand = args.length >= 2 ? args[1] : "";
                    arguments = args.length >= 3 ? Arrays.copyOfRange(args, 2, args.length) : new String[0];
                    extra = subType;
                    break;
                case "reload":
                    commandFactory = new ElytraBoosterCommandFactory();
                    subCommand = "reload";
                    break;
                default:
                    commandFactory = new ElytraBoosterCommandFactory();
                    subCommand = "help";
            }

            commandFactory.buildCommand(plugin, sender, subCommand, arguments, isSenderPlayer, extra).checkExecution();
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        final List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], SUBTYPES, completions);
        } else if (args.length == 2) {
            StringUtil.copyPartialMatches(args[1], BOOSTER_SUBCOMMANDS, completions);
        }
        Collections.sort(completions);
        return completions;
    }

}
