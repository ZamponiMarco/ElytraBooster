package com.github.jummes.elytrabooster.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.jummes.elytrabooster.boosters.portals.AbstractPortal;
import com.github.jummes.elytrabooster.core.ElytraBooster;

public class FinishedCooldownEvent extends Event {

	private static final HandlerList HANDLERS_LIST = new HandlerList();
	private AbstractPortal portal;
	
	public FinishedCooldownEvent(ElytraBooster plugin, AbstractPortal portal) {
		this.portal = portal;
	}
	
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}


	public AbstractPortal getPortal() {
		return portal;
	}

}
