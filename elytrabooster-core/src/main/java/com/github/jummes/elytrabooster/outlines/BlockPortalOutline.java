package com.github.jummes.elytrabooster.outlines;

import java.util.List;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

import com.google.common.base.Predicate;

public class BlockPortalOutline implements PortalOutline {

	private Material outlineType;
	private Material cooldownType;

	public BlockPortalOutline(String outlineType, String cooldownType) {
		try {
			this.outlineType = Material.valueOf(outlineType.toUpperCase());
			this.cooldownType = Material.valueOf(cooldownType.toUpperCase());
		} catch (IllegalArgumentException e) {
			this.outlineType = Material.STONE;
			this.cooldownType = Material.STONE;
			Bukkit.getLogger().warning(
					ChatColor.RED + outlineType + " or " + cooldownType + " is not a block, check portals.yml");
		}
	}

	@Override
	public void drawOutline(List<Location> points) {
		drawOutline(points, outlineType, false);
	}

	private void drawOutline(List<Location> points, Material m, boolean force) {

		Predicate<? super Location> predicate = force
				? point -> point.getBlock().getType().equals(outlineType)
						|| point.getBlock().getType().equals(cooldownType)
				: point -> point.getBlock().getType().equals(Material.AIR)
						|| point.getBlock().getType().equals(cooldownType);

		points.stream().filter(predicate).forEach(point -> point.getBlock().setType(m));
	}

	@Override
	public void eraseOutline(List<Location> points) {
		drawOutline(points, Material.AIR, true);
	}

	@Override
	public void cooldownOutline(List<Location> points, int cooldown, int progress) {
		int cooldownBlocks = (int) ((progress / (double) cooldown) * points.size());
		IntStream.range(0, cooldownBlocks).forEach(i -> {
			if (!points.get(i).getBlock().getType().equals(cooldownType)) {
				points.get(i).getBlock().setType(cooldownType);
			}
		});
		IntStream.range(cooldownBlocks, points.size()).forEach(i -> {
			if (!points.get(i).getBlock().getType().equals(outlineType)) {
				points.get(i).getBlock().setType(outlineType);
			}
		});
	}

	@Override
	public Object getOutlineType() {
		return outlineType;
	}

	@Override
	public Object getCooldownType() {
		return cooldownType;
	}

}
