package com.github.jummes.elytrabooster.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.util.MessageUtils;

public class ElytraBoosterHelpCommand extends AbstractCommand {

    public ElytraBoosterHelpCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
                                    boolean isSenderPlayer) {
        super(plugin, sender, subCommand, arguments, isSenderPlayer);
    }

    private List<String> pages;

    @Override
    protected void execute() {
        pages = new ArrayList<String>();
        setUpPages();
        int numberOfPages = 1;

        int pageToPrint;
        if (arguments.length >= 1 && StringUtils.isNumeric(arguments[0])
                && Integer.valueOf(arguments[0]) > numberOfPages) {
            pageToPrint = Integer.valueOf(arguments[0]) - 1;
        } else {
            pageToPrint = 0;
        }

        sender.sendMessage(pages.get(pageToPrint));

    }

    @Override
    protected boolean isOnlyPlayer() {
        return false;
    }

    private void setUpPages() {
        StringBuilder page = new StringBuilder();
        page.append(MessageUtils.header("ElytraBooster Help"));
        page.append(MessageUtils.color(String.format("&2/eb help &c[page] &7Print the help message.\n"
                + "&2/eb reload &7Reload ElytraBooster config files.\n"
                + "&2/eb portal help &7Print the portals help message.\n"
                + "&2/eb spawner help &7Print the spawners help message.\n"
                + "&2/eb pad help &7Print the pads help message.\n")));
        pages.add(page.toString());
    }

    @Override
    protected Permission getPermission() {
        return new Permission("eb.admin.help");
    }

}
