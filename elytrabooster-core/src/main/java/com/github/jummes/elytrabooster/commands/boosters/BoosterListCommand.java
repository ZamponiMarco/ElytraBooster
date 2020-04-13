package com.github.jummes.elytrabooster.commands.boosters;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.gui.model.ModelCollectionInventoryHolder;
import com.github.jummes.libs.model.ModelManager;

public class BoosterListCommand extends BoosterCommand {

    public BoosterListCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
                              boolean isSenderPlayer, String boosterString) {
        super(plugin, sender, subCommand, arguments, isSenderPlayer, boosterString);
    }

    @Override
    protected void execute() {
        Player p = (Player) sender;
        ModelManager<?> manager = null;
        switch (boosterString) {
            case "portal":
                manager = ElytraBooster.getInstance().getPortalManager();
                break;
            case "spawner":
                manager = ElytraBooster.getInstance().getSpawnerManager();
                break;
        }
        try {
            p.openInventory(new ModelCollectionInventoryHolder(plugin, manager, boosterString + "s").getInventory());
        } catch (NoSuchFieldException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isOnlyPlayer() {
        return true;
    }

    @Override
    protected Permission getPermission() {
        return new Permission("eb.admin.list");
    }

}
