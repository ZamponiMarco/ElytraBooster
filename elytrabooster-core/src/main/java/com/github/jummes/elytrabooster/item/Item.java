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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Item implements Model {

    private static final String ACTION_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzdlNmM0MGY2OGI3NzVmMmVmY2Q3YmQ5OTE2YjMyNzg2OWRjZjI3ZTI0Yzg1NWQwYTE4ZTA3YWMwNGZlMSJ9fX0=";

    @Serializable(headTexture = ACTION_HEAD)
    private String id;
    @Serializable(headTexture = ACTION_HEAD)
    private ItemStackWrapper item;
    @Serializable(headTexture = ACTION_HEAD)
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
        return item.getWrapped();
    }
}
