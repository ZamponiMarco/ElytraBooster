package com.github.jummes.elytrabooster.listener;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.util.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) && Objects.equals(e.getHand(), EquipmentSlot.HAND) && e.getItem() != null) {
            ElytraBooster.getInstance().getItemManager().getItems().stream()
                    .filter(item -> ItemUtils.isSimilar(item.getItem().getWrapped(), e.getItem()))
                    .findFirst().ifPresent(item -> {
                item.boostPlayer(player);
                e.setCancelled(true);
            });
        }
    }

}
