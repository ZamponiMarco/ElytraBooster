package com.github.jummes.elytrabooster.spawner;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.spawner.entityholder.EntityHolder;
import com.github.jummes.elytrabooster.spawner.volume.SphericVolume;
import com.github.jummes.elytrabooster.spawner.volume.Volume;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.util.ItemUtils;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Getter
public class Spawner implements Model {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    protected ElytraBooster plugin;
    protected int spawnTaskNumber;
    @Serializable(headTexture = HEAD)
    private String id;
    @Serializable(headTexture = HEAD)
    private Volume volume;
    @Serializable(headTexture = HEAD)
    private int cooldown;
    @Serializable(headTexture = HEAD)
    private EntityHolder holder;

    public Spawner(Player p) {
        this(RandomStringUtils.randomAlphabetic(6), new SphericVolume(p), 20, new EntityHolder());
    }

    public Spawner(String id, Volume volume, int cooldown, EntityHolder holder) {
        this.plugin = ElytraBooster.getInstance();
        this.id = id;
        this.volume = volume;
        this.cooldown = cooldown;
        this.holder = holder;
        runBoosterTask();
    }

    public static Spawner deserialize(Map<String, Object> map) {
        String id = (String) map.get("id");
        Volume volume = (Volume) map.get("volume");
        int cooldown = (int) map.get("cooldown");
        EntityHolder holder = (EntityHolder) map.get("holder");
        return new Spawner(id, volume, cooldown, holder);
    }

    public void runBoosterTask() {
        this.spawnTaskNumber = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> spawnEntity(), 0, cooldown)
                .getTaskId();
    }

    private void spawnEntity() {
        holder.spawnEntity(volume.getRandomPoint());
    }

    // ---

    public void stopBoosterTask() {
        plugin.getServer().getScheduler().cancelTask(spawnTaskNumber);
        holder.despawnAll();
    }

    @Override
    public ItemStack getGUIItem() {
        return ItemUtils.getNamedItem(new ItemStack(Material.CARROT), "&6&l" + id, Lists.newArrayList());
    }

    /**
     * When the portal is deleted stop the booster task
     */
    @Override
    public void onRemoval() {
        stopBoosterTask();
    }

    /**
     * If a setting is modified reload the portal tasks
     */
    @Override
    public void onModify() {
        stopBoosterTask();
        runBoosterTask();
    }

}
