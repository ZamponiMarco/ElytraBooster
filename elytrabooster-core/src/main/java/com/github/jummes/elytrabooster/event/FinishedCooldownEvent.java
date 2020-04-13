package com.github.jummes.elytrabooster.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.jummes.elytrabooster.portal.Portal;

public class FinishedCooldownEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Portal portal;

    public FinishedCooldownEvent(Portal portal) {
        this.portal = portal;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }


    public Portal getPortal() {
        return portal;
    }

}
