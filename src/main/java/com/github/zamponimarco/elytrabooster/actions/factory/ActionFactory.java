package com.github.zamponimarco.elytrabooster.actions.factory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.github.zamponimarco.elytrabooster.actions.AbstractAction;
import com.github.zamponimarco.elytrabooster.actions.EffectAction;
import com.github.zamponimarco.elytrabooster.actions.ParticleAction;
import com.github.zamponimarco.elytrabooster.actions.SoundAction;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;

public class ActionFactory {

	public static AbstractAction buildAction(ElytraBooster plugin, String actionString, Player player) {
		Map<String, String> parameters = new HashMap<String, String>();
		int firstBracketIndex = actionString.indexOf("[");
		String actionType = actionString.substring(0, firstBracketIndex);
		String parametersString = actionString.substring(firstBracketIndex + 1, actionString.length() - 1);
		parametersString = parsePlaceholders(parametersString, player);
		Arrays.stream(parametersString.split(";")).forEach(string -> {
			String[] keyValue = string.split("=");
			parameters.put(keyValue[0], keyValue[1]);
		});

		switch (actionType) {
		case "effect":
			return new EffectAction(plugin, parameters);
		case "particle":
			return new ParticleAction(plugin, parameters);
		case "sound":
			return new SoundAction(plugin, parameters);
		}
		return null;
	}

	private static String parsePlaceholders(String parametersString, Player player) {
		String toReturn = parametersString;
		toReturn = toReturn.replaceAll("\\{player.name\\}", player.getName());
		toReturn = toReturn.replaceAll("\\{player.x\\}", String.valueOf(player.getLocation().getX()));
		toReturn = toReturn.replaceAll("\\{player.y\\}", String.valueOf(player.getLocation().getY()));
		toReturn = toReturn.replaceAll("\\{player.z\\}", String.valueOf(player.getLocation().getZ()));
		toReturn = toReturn.replaceAll("\\{player.world\\}", player.getLocation().getWorld().getName());
		return toReturn;
	}

}
