package com.github.jummes.elytrabooster.command;

import com.github.jummes.libs.command.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.gui.model.ModelCollectionInventoryHolder;
import com.github.jummes.libs.model.ModelManager;

public class BoosterListCommand extends AbstractCommand {

    private String boosterString;

    public BoosterListCommand(CommandSender sender, String subCommand, String[] arguments,
                              boolean isSenderPlayer, String boosterString) {
        super(sender, subCommand, arguments, isSenderPlayer);
        this.boosterString = boosterString;
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
            case "pad":
                manager = ElytraBooster.getInstance().getPadManager();
                break;
        }
        try {
            p.openInventory(new ModelCollectionInventoryHolder(ElytraBooster.getInstance(), manager, boosterString + "s").getInventory());
        } catch (NoSuchFieldException | SecurityException e) {
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
