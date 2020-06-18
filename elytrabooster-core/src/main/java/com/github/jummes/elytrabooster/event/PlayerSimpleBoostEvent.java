package com.github.jummes.elytrabooster.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class PlayerSimpleBoostEvent extends PlayerBoostEvent {

    @Getter
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Player player;
    private boolean cancelled;

    public PlayerSimpleBoostEvent(Player player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        cancelled = isCancelled;
    }

}
