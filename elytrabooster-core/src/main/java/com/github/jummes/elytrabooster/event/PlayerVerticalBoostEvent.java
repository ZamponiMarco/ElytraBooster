package com.github.jummes.elytrabooster.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

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
