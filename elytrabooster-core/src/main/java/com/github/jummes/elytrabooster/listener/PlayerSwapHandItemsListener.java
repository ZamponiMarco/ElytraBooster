package com.github.jummes.elytrabooster.listener;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.event.PlayerVerticalBoostEvent;
import com.github.jummes.elytrabooster.manager.PadManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerSwapHandItemsListener implements Listener {

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent e) {
        PadManager padManager = ElytraBooster.getInstance().getPadManager();
        Player player = e.getPlayer();
        if (player.hasPermission("eb.boosters.boost") && player.getEquipment().getChestplate() != null
                && player.getEquipment().getChestplate().getType().equals(Material.ELYTRA) && !player.isGliding()) {
            padManager.getPads().stream()
                    .filter(pad -> player.getWorld().equals(pad.getCenter().getWorld())
                            && player.getLocation().distance(pad.getCenter()) <= 1)
                    .findFirst().ifPresent(pad -> {
                pad.cooldown();
                pad.getVisual().onBoost(player.getLocation());
                Bukkit.getPluginManager()
                        .callEvent(new PlayerVerticalBoostEvent(ElytraBooster.getInstance(), player, pad.getBoost()));
                e.setCancelled(true);
            });
        }
    }

}
