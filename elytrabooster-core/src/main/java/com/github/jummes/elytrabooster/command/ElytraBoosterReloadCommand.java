package com.github.jummes.elytrabooster.command;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.pad.Pad;
import com.github.jummes.elytrabooster.portal.Portal;
import com.github.jummes.elytrabooster.spawner.Spawner;
import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.core.Libs;
import com.github.jummes.libs.util.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class ElytraBoosterReloadCommand extends AbstractCommand {

    public ElytraBoosterReloadCommand(CommandSender sender, String subCommand, String[] arguments,
                                      boolean isSenderPlayer) {
        super(sender, subCommand, arguments, isSenderPlayer);
    }

    @Override
    protected void execute() {
        sender.sendMessage(Libs.getLocale().get("command.reload.start"));
        Bukkit.getScheduler().cancelTasks(ElytraBooster.getInstance());
        Libs.getLocale().reloadData(ElytraBooster.getInstance().getConfig().getString("locale"));
        ElytraBooster.getInstance().getPortalManager().reloadData();
        ElytraBooster.getInstance().getSpawnerManager().reloadData();
        ElytraBooster.getInstance().getPadManager().reloadData();
        sender.sendMessage(Libs.getLocale().get("command.reload.success"));
    }

    @Override
    protected boolean isOnlyPlayer() {
        return false;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("eb.admin.reload");
    }

}
