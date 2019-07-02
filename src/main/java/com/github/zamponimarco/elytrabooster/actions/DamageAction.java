package com.github.zamponimarco.elytrabooster.actions;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class DamageAction extends AbstractAction {

	private Player target;
	private int amount;

	public DamageAction(ElytraBooster plugin, Map<String, String> parameters) {
		super(plugin, parameters);
	}

	@Override
	protected void parseParameters(Map<String, String> parameters) {
		target = Bukkit.getPlayer(parameters.get("player"));

		amount = Integer.valueOf(parameters.getOrDefault("amount", "2"));
	}

	@Override
	public void executeAction() {
		target.damage(amount);
	}

}
