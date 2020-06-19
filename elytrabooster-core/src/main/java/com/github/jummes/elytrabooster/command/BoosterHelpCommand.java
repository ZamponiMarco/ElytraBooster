package com.github.jummes.elytrabooster.command;

import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.util.MessageUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

public class BoosterHelpCommand extends AbstractCommand {

    private List<String> pages;
    private String boosterString;

    public BoosterHelpCommand(CommandSender sender, String subCommand, String[] arguments,
                              boolean isSenderPlayer, String boosterString) {
        super(sender, subCommand, arguments, isSenderPlayer);
        this.boosterString = boosterString;
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

    @Override
    protected Permission getPermission() {
        return new Permission("eb.admin.help");
    }

    private void setUpPages() {
        String help = "&2/eb booster help &7Print the boosters help message.\n".replaceAll("booster", boosterString) +
                "&2/eb booster list &7List boosters.\n".replaceAll("booster", boosterString);
        help +=
                boosterString.equals("item") ? "" : "&2/eb booster near &c[radius] &7List all the boosters within [radius] blocks.\n".
                        replaceAll("booster", boosterString);
        String page = MessageUtils.header("ElytraBooster Help") +
                MessageUtils.color(help);
        pages.add(page);
    }

}
