package com.github.jummes.elytrabooster.actions;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.utils.MessageUtils;

public class MessageAction extends AbstractAction{

	private Player target;
	private String message;
	
	public MessageAction(ElytraBooster plugin, Map<String, String> parameters) {
		super(plugin, parameters);
	}

	@Override
	protected void parseParameters(Map<String, String> parameters) {
		target = Bukkit.getPlayer(parameters.get("player"));

		message = parameters.get("message");
	}

	@Override
	public void executeAction() {
		target.sendMessage(MessageUtils.color(message));
	}

}
