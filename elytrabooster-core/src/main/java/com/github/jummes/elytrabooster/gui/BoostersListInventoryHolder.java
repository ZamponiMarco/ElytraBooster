package com.github.jummes.elytrabooster.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.github.jummes.elytrabooster.boosters.Booster;
import com.github.jummes.elytrabooster.boosters.pads.AbstractPad;
import com.github.jummes.elytrabooster.boosters.portals.AbstractPortal;
import com.github.jummes.elytrabooster.boosters.spawners.AbstractSpawner;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.gui.factory.SettingsInventoryHolderFactory;
import com.github.jummes.elytrabooster.managers.boosters.BoosterManager;
import com.github.jummes.elytrabooster.utils.MessageUtils;
import com.google.common.collect.Lists;

public class BoostersListInventoryHolder extends ElytraBoosterInventoryHolder {

	private static final int BOOSTERS_NUMBER = 50;
	private static final String ARROW_LEFT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzY5N2MyNDg5MmNmYzAzYzcyOGZmYWVhYmYzNGJkZmI5MmQ0NTExNDdiMjZkMjAzZGNhZmE5M2U0MWZmOSJ9fX0=";
	private static final String ARROW_RIGHT_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZlMTQ1ZTcxMjk1YmNjMDQ4OGU5YmI3ZTZkNjg5NWI3Zjk2OWEzYjViYjdlYjM0YTUyZTkzMmJjODRkZjViIn19fQ===";
	private static final String ACTIVE_PORTAL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTIxOTI4ZWE2N2QzYThiOTdkMjEyNzU4ZjE1Y2NjYWMxMDI0Mjk1YjE4NWIzMTkyNjQ4NDRmNGM1ZTFlNjFlIn19fQ=====";
	private static final String NOT_ACTIVE_PORTAL_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjVlZjY4ZGNiZDU4MjM0YmE3YWVlMmFkOTFjYTZmYTdjZTIzZjlhMzIzNDViNDhkNmU1ZjViODZhNjhiNWIifX19===";
	private static final String SPAWNER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQ3ZTJlNWQ1NWI2ZDA0OTQzNTE5YmVkMjU1N2M2MzI5ZTMzYjYwYjkwOWRlZTg5MjNjZDg4YjExNTIxMCJ9fX0======";
	private static final String PAD_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTA1YTJjYWI4YjY4ZWE1N2UzYWY5OTJhMzZlNDdjOGZmOWFhODdjYzg3NzYyODE5NjZmOGMzY2YzMWEzOCJ9fX0=======";

	private String title;
	private String boosterString;
	private int page;
	private Location center;
	private int range;

	public BoostersListInventoryHolder(String title, String boosterString, int page, Location center, int range) {
		this.title = title;
		this.boosterString = boosterString;
		this.page = page;
		this.center = center;
		this.range = range;
	}

	public BoostersListInventoryHolder(String title, String boosterString, int page, Location center) {
		this(title, boosterString, page, center, Integer.MAX_VALUE);
	}

	@Override
	protected void initializeInventory() {
		BoosterManager<?> manager = BoosterManager.getBoosterManager(boosterString);

		List<Booster> boosters = Lists.newArrayList(manager.getBoostersMap().values());
		List<Booster> toList = boosters.stream()
				.filter(booster -> booster.getCenter().getWorld().equals(center.getWorld())
						&& booster.getCenter().distance(center) <= range
						&& boosters.indexOf(booster) >= (page - 1) * BOOSTERS_NUMBER
						&& boosters.indexOf(booster) <= page * BOOSTERS_NUMBER - 1)
				.collect(Collectors.toList());

		int maxPage = (int) Math.ceil((boosters.size() > 0 ? boosters.size() : 1) / (double) BOOSTERS_NUMBER);

		this.inventory = Bukkit.createInventory(this, 54, title);
		toList.forEach(booster -> registerClickConsumer(toList.indexOf(booster), getBoosterItem(booster),
				getBoosterConsumer(boosters, toList.indexOf(booster))));
		registerClickConsumer(51, getCreateItem(), getCreateConsumer(boosterString));
		if (page != maxPage) {
			registerClickConsumer(53, ElytraBooster.getInstance().getWrapper().skullFromValue(ARROW_LEFT_HEAD), e -> e.getWhoClicked().openInventory(
					new BoostersListInventoryHolder(title, boosterString, page + 1, center, range).getInventory()));
		}
		if (page != 1) {
			registerClickConsumer(52, ElytraBooster.getInstance().getWrapper().skullFromValue(ARROW_RIGHT_HEAD), e -> e.getWhoClicked().openInventory(
					new BoostersListInventoryHolder(title, boosterString, page - 1, center, range).getInventory()));
		}
		fillInventoryWith(Material.GRAY_STAINED_GLASS_PANE);
	}

	private ItemStack getBoosterItem(Booster booster) {
		ItemStack portalItem = getBoosterSkull(booster);
		ItemMeta meta = portalItem.getItemMeta();
		meta.setDisplayName(MessageUtils.color("&a&l" + booster.getId()));
		meta.setLore(getBoosterItemLore(booster));
		portalItem.setItemMeta(meta);
		return portalItem;
	}

	private ItemStack getBoosterSkull(Booster booster) {
		if (booster instanceof AbstractPortal) {
			return ((AbstractPortal) booster).isActive() ? ElytraBooster.getInstance().getWrapper().skullFromValue(ACTIVE_PORTAL_HEAD)
					: ElytraBooster.getInstance().getWrapper().skullFromValue(NOT_ACTIVE_PORTAL_HEAD);
		} else if (booster instanceof AbstractSpawner) {
			return ElytraBooster.getInstance().getWrapper().skullFromValue(SPAWNER_HEAD);
		} else if (booster instanceof AbstractPad) {
			return ElytraBooster.getInstance().getWrapper().skullFromValue(PAD_HEAD);
		}
		return null;
	}

	private List<String> getBoosterItemLore(Booster booster) {
		List<String> lore = new ArrayList<String>();
		lore.add(MessageUtils.color(String.format("&6&l- world: &a%s", booster.getCenter().getWorld().getName())));
		lore.add(MessageUtils.color(String.format("&6&l- x/y/z: &a%.2f&6&l/&a%.2f&6&l/&a%.2f",
				booster.getCenter().getX(), booster.getCenter().getY(), booster.getCenter().getZ())));
		lore.add(MessageUtils.color("&6&l- &e&lLeft click &6to open settings"));
		lore.add(MessageUtils.color("&6&l- &e&lRight click &6to teleport"));
		lore.add(MessageUtils.color("&6&l- &e&lMiddle click &6to move the portal to you"));
		return lore;
	}

	private Consumer<InventoryClickEvent> getBoosterConsumer(List<Booster> boosters, int slot) {
		return e -> {
			Booster booster = boosters.get(slot);
			if (e.getClick().equals(ClickType.LEFT)) {
				e.getWhoClicked().openInventory(SettingsInventoryHolderFactory
						.buildSettingsInventoryHolder(boosterString, booster.getId(), this).getInventory());
			} else if (e.getClick().equals(ClickType.RIGHT)) {
				e.getWhoClicked().teleport(booster.getCenter(), TeleportCause.PLUGIN);
			} else if (e.getClick().equals(ClickType.MIDDLE)) {
				movePortal(e.getWhoClicked(), booster);
			}
		};
	}

	private void movePortal(HumanEntity player, Booster booster) {
		BoosterManager<?> boosterManager = BoosterManager.getBoosterManager(boosterString);

		ConfigurationSection section = (ConfigurationSection) boosterManager.getDataYaml().get(booster.getId());
		World world = player.getLocation().getWorld();
		double x = player.getLocation().getBlockX();
		double y = player.getLocation().getBlockY();
		double z = player.getLocation().getBlockZ();
		section.set("world", world.getName());
		section.set("x", x);
		section.set("y", y);
		section.set("z", z);

		if (booster instanceof AbstractPortal && !((AbstractPortal) booster).getPortalsUnion().isEmpty()) {
			Location oldLocation = booster.getCenter();
			Location newLocation = new Location(world, x, y, z);
			Vector movement = newLocation.clone().subtract(oldLocation.clone()).toVector();
			List<String> portalsUnion = new ArrayList<String>();
			((AbstractPortal) booster).getPortalsUnion().forEach(portal -> {
				portal.setCenter(portal.getCenter().clone().add(movement));
				portalsUnion.add(portal.toString());
			});
			section.set("portalsUnion", portalsUnion);
		}
		booster.stopBoosterTask();
		boosterManager.saveConfig();
		boosterManager.addBooster(booster.getId());
	}

}
