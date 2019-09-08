package com.github.zamponimarco.elytrabooster.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.zamponimarco.elytrabooster.boosters.Booster;
import com.github.zamponimarco.elytrabooster.core.ElytraBooster;
import com.github.zamponimarco.elytrabooster.gui.settings.SettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.gui.settings.StringSettingInventoryHolder;
import com.github.zamponimarco.elytrabooster.managers.boosters.BoosterManager;
import com.github.zamponimarco.elytrabooster.utils.HeadUtils;
import com.github.zamponimarco.elytrabooster.utils.MessageUtils;
import com.google.common.collect.Lists;

public abstract class ElytraBoosterInventoryHolder implements InventoryHolder {

	private static final String CREATE_ITEM_TITLE = "&a&lCreate Booster";
	private static final String DELETE_ITEM_TITLE = "&a&lDelete Booster";

	private static final String DELETE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTljZGI5YWYzOGNmNDFkYWE1M2JjOGNkYTc2NjVjNTA5NjMyZDE0ZTY3OGYwZjE5ZjI2M2Y0NmU1NDFkOGEzMCJ9fX0=";
	private static final String CREATE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTcxZDg5NzljMTg3OGEwNTk4N2E3ZmFmMjFiNTZkMWI3NDRmOWQwNjhjNzRjZmZjZGUxZWExZWRhZDU4NTIifX19=";

	private static final String BACK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZlMTQ1ZTcxMjk1YmNjMDQ4OGU5YmI3ZTZkNjg5NWI3Zjk2OWEzYjViYjdlYjM0YTUyZTkzMmJjODRkZjViIn19fQ====";

	protected ElytraBooster plugin;
	protected Inventory inventory;
	protected Map<Integer, Consumer<InventoryClickEvent>> clickMap;

	public ElytraBoosterInventoryHolder() {
		this.plugin = ElytraBooster.getInstance();
		this.clickMap = new HashMap<>();
	}

	protected abstract void initializeInventory();

	@Override
	public Inventory getInventory() {
		initializeInventory();
		return inventory;
	}

	public void handleClickEvent(InventoryClickEvent e) {
		clickMap.get(e.getSlot()).accept(e);
		e.setCancelled(true);
	}

	public void registerClickConsumer(int slot, ItemStack item, Consumer<InventoryClickEvent> clickConsumer) {
		inventory.setItem(slot, item);
		clickMap.put(slot, clickConsumer);
	}

	protected void fillInventoryWith(Material material) {
		for (int i = 0; i < inventory.getSize(); i++) {
			if (inventory.getItem(i) == null) {
				registerClickConsumer(i, getPlaceholderItem(material), e -> {
				});
			}
		}
	}

	private ItemStack getPlaceholderItem(Material material) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return item;
	}

	protected Consumer<InventoryClickEvent> getDeleteConsumer(BoosterManager<?> manager, ConfigurationSection section) {
		return e -> {
			Booster booster = manager.getBooster(section.getName());
			booster.stopBoosterTask();
			manager.removeBooster(booster.getId());
			section.getParent().set(section.getName(), null);
			manager.saveConfig();
			e.getWhoClicked().sendMessage(MessageUtils.color("&aBooster deleted, &6ID: &a" + booster.getId()));
			e.getWhoClicked().closeInventory();
		};
	}

	protected ItemStack getDeleteItem() {
		ItemStack item = HeadUtils.skullFromValue(DELETE_HEAD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessageUtils.color(DELETE_ITEM_TITLE));
		meta.setLore(Lists.newArrayList(MessageUtils.color("&6&l- &e&lLeft click &6to delete booster")));
		item.setItemMeta(meta);
		return item;
	}

	protected Consumer<InventoryClickEvent> getCreateConsumer(String boosterString) {
		return e -> {
			BoosterManager<?> manager = BoosterManager.getBoosterManager(boosterString);
			ConfigurationSection section = manager.getDataYaml().getRoot();
			e.getWhoClicked().openInventory(
					new StringSettingInventoryHolder(manager, section, null, null, e.getWhoClicked(), this)
							.getInventory());
		};
	}

	protected ItemStack getCreateItem() {
		ItemStack item = HeadUtils.skullFromValue(CREATE_HEAD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessageUtils.color(CREATE_ITEM_TITLE));
		meta.setLore(Lists.newArrayList(MessageUtils.color("&6&l- &e&lLeft click &6to create booster")));
		item.setItemMeta(meta);
		return item;
	}

	protected ItemStack getBackItem() {
		ItemStack item = HeadUtils.skullFromValue(BACK_HEAD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(MessageUtils.color("&6&lGo back"));
		item.setItemMeta(meta);
		return item;
	}

	protected Consumer<InventoryClickEvent> getSettingConsumer(BoosterManager<?> dataManager,
			ConfigurationSection section, String key, Object value, Class<? extends SettingInventoryHolder> clazz) {
		return e -> {
			if (e.getClick().equals(ClickType.LEFT)) {
				HumanEntity p = e.getWhoClicked();
				try {
					p.openInventory(clazz
							.getConstructor(BoosterManager.class, ConfigurationSection.class, String.class,
									Object.class, HumanEntity.class, InventoryHolder.class)
							.newInstance(dataManager, section, key, value, p, this).getInventory());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

}
