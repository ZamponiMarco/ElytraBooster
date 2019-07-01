package com.github.zamponimarco.elytrabooster.utils;

import org.bukkit.ChatColor;

public class MessagesUtil {

	/**
	 * Colors mc strings
	 * 
	 * @param string string to be colored
	 * @return colored string
	 */
	public static String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	/**
	 * Creates a string that consists in the repetition of the input string
	 * 
	 * @param count number of times to repeat the string
	 * @param with string to repeat
	 * @return result string
	 */
	public static String repeat(int count, String with) {
	    return new String(new char[count]).replace("\0", with);
	}
	
	public static String header(String string) {
		return MessagesUtil.color(String.format("&e=--- &c%s &e---=\n", string));
	}
	
	public static String delimiter() {
		return MessagesUtil.color("&e-----------------------------------------------------");
	}
	
	public static String footer(int page, int numberOfPages) {
		return MessagesUtil.color(String.format("&e=--- &cPage &6%d&c/&6%d &e---=", page, numberOfPages));
	}
	
}
