package com.github.jummes.elytrabooster.command;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.pad.Pad;
import com.github.jummes.elytrabooster.portal.Portal;
import com.github.jummes.elytrabooster.spawner.Spawner;
import com.github.jummes.libs.command.AbstractCommand;
import com.github.jummes.libs.gui.model.ModelCollectionInventoryHolder;
import com.github.jummes.libs.model.ModelManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.Objects;
import java.util.function.Predicate;

public class BoosterNearCommand extends AbstractCommand {

    private String boosterString;

    public BoosterNearCommand(CommandSender sender, String subCommand, String[] arguments,
                              boolean isSenderPlayer, String boosterString) {
        super(sender, subCommand, arguments, isSenderPlayer);
        this.boosterString = boosterString;
    }

    @Override
    protected void execute() {
        if (arguments.length >= 1 && StringUtils.isNumeric(arguments[0])) {
        } else {
            return;
        }
        double distance = Double.parseDouble(arguments[0]);
        Player p = (Player) sender;
        ModelManager<?> manager = null;
        Predicate<?> predicate = obj -> true;
        switch (boosterString) {
            case "portal":
                manager = ElytraBooster.getInstance().getPortalManager();
                predicate = obj -> {
                    Location l = ((Portal) obj).getShape().getCenterPoint();
                    return Objects.equals(l.getWorld(), p.getWorld()) && l.distance(p.getLocation()) < distance;
                };
                break;
            case "spawner":
                manager = ElytraBooster.getInstance().getSpawnerManager();
                predicate = obj -> {
                    Location l = ((Spawner) obj).getVolume().getCenterPoint();
                    return Objects.equals(l.getWorld(), p.getWorld()) && l.distance(p.getLocation()) < distance;
                };
                break;
            case "pad":
                manager = ElytraBooster.getInstance().getPadManager();
                predicate = obj -> {
                    Location l = ((Pad) obj).getCenter();
                    return Objects.equals(l.getWorld(), p.getWorld()) && l.distance(p.getLocation()) < distance;
                };
                break;
        }
        try {
            p.openInventory(new ModelCollectionInventoryHolder(ElytraBooster.getInstance(), manager, boosterString + "s", predicate).getInventory());
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
        return new Permission("eb.admin.near");
    }

}
