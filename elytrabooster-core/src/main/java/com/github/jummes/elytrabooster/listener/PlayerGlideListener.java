package com.github.jummes.elytrabooster.listener;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class PlayerGlideListener implements Listener {

    @EventHandler
    public void onPlayerGlideEvent(EntityToggleGlideEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.isGliding()) {
                ElytraBooster.getInstance().getStatusMap().put(p, false);
            } else {
                ElytraBooster.getInstance().getStatusMap().remove(p);
            }
        }
    }

}
