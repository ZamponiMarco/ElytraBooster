package com.github.jummes.elytrabooster.item;

import com.github.jummes.elytrabooster.boost.Boost;
import com.github.jummes.elytrabooster.boost.SimpleBoost;
import com.github.jummes.elytrabooster.boost.VerticalBoost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.event.PlayerBoostEvent;
import com.github.jummes.elytrabooster.event.PlayerSimpleBoostEvent;
import com.github.jummes.elytrabooster.event.PlayerVerticalBoostEvent;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.wrapper.ItemStackWrapper;
import com.github.jummes.libs.util.ItemUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Item implements Model {

    private static final String ID_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RkNjM5NzhlODRlMjA5MjI4M2U5Y2QwNmU5ZWY0YmMyMjhiYjlmMjIyMmUxN2VlMzgzYjFjOWQ5N2E4YTAifX19";
    private static final String BOOST_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAyZjQ4ZjM0ZDIyZGVkNzQwNGY3NmU4YTEzMmFmNWQ3OTE5YzhkY2Q1MWRmNmU3YTg1ZGRmYWM4NWFiIn19fQ==";

    @Serializable(headTexture = ID_HEAD, description = "gui.item.id")
    private String id;
    @Serializable(displayItem = "getFlatItem", description = "gui.item.item")
    private ItemStackWrapper item;
    @Serializable(headTexture = BOOST_HEAD, description = "gui.item.boost", recreateTooltip = true)
    private Boost boost;

    public Item() {
        this(RandomStringUtils.randomAlphabetic(6), new ItemStackWrapper(new ItemStack(Material.FIREWORK_ROCKET)), new SimpleBoost());
    }

    public static Item deserialize(Map<String, Object> map) {
        String id = (String) map.get("id");
        ItemStackWrapper item = (ItemStackWrapper) map.get("item");
        Boost boost = (Boost) map.get("boost");
        return new Item(id, item, boost);
    }

    public void boostPlayer(Player player) {
        if (player.hasPermission("eb.boosters.boost") && player.getEquipment().getChestplate() != null
                && player.getEquipment().getChestplate().getType().equals(Material.ELYTRA)) {
            PlayerBoostEvent event;
            if (boost instanceof SimpleBoost && ElytraBooster.getInstance().getStatusMap().containsKey(player)
                    && !ElytraBooster.getInstance().getStatusMap().get(player)) {
                event = new PlayerSimpleBoostEvent(player);
            } else if (boost instanceof VerticalBoost && !player.isGliding() && player.isOnGround()) {
                event = new PlayerVerticalBoostEvent(player);
            } else {
                return;
            }
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                boost.boostPlayer(player);
            }
            int amount = player.getInventory().getItemInMainHand().getAmount();
            if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                player.getInventory().getItemInMainHand().setAmount(--amount);
            }
        }
    }

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(getFlatItem(), "&6&l" + id, new ArrayList<>());
    }

    public ItemStack getFlatItem() {
        return item.getWrapped().clone();
    }
}
