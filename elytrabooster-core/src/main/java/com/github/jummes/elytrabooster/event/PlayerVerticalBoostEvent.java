package com.github.jummes.elytrabooster.event;

import com.github.jummes.elytrabooster.action.targeter.LocationTarget;
import com.github.jummes.elytrabooster.action.targeter.PlayerTarget;
import com.github.jummes.elytrabooster.boost.VerticalBoost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@Getter
@Setter
public class PlayerVerticalBoostEvent extends PlayerBoostEvent {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Player player;
    private boolean cancelled;

    public PlayerVerticalBoostEvent(Player player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

}
