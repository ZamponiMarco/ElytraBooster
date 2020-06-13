package com.github.jummes.elytrabooster.command;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.util.MessageUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

public class ElytraBoosterHelpCommand extends AbstractCommand {

    private List<String> pages;

    public ElytraBoosterHelpCommand(CommandSender sender, String subCommand, String[] arguments,
                                    boolean isSenderPlayer) {
        super(sender, subCommand, arguments, isSenderPlayer);
    }

    @Override
    protected void execute() {
        pages = new ArrayList<>();
        setUpPages();
        int numberOfPages = 1;

        int pageToPrint;
        if (arguments.length >= 1 && StringUtils.isNumeric(arguments[0])
                && Integer.parseInt(arguments[0]) > numberOfPages) {
            pageToPrint = Integer.parseInt(arguments[0]) - 1;
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
        String page = MessageUtils.header("ElytraBooster Help") +
                MessageUtils.color(String.format("&2/eb help &c[page] &7Print the help message.\n"
                        + "&2/eb reload &7Reload ElytraBooster config files.\n"
                        + "&2/eb portal help &7Print the portals help message.\n"
                        + "&2/eb spawner help &7Print the spawners help message.\n"
                        + "&2/eb pad help &7Print the pads help message.\n"));
        pages.add(page);
    }

    @Override
    protected Permission getPermission() {
        return new Permission("eb.admin.help");
    }

}
