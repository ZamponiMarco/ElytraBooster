package com.github.zamponimarco.elytrabooster.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class HeadsUtil {

	/**
	 * Gets a skull from a texture value
	 * 
	 * @param value texture of the skull
	 * @return the skull to be created
	 */
	public static ItemStack skullFromValue(String value) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		UUID id = new UUID(value.hashCode(), value.hashCode());
		return Bukkit.getUnsafe().modifyItemStack(item,
				"{SkullOwner:{Id:\"" + id + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}");
	}
	
}
