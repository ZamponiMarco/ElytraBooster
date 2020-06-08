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
import sun.security.provider.SHA;

import java.util.Map;

@Getter
public class Spawner implements Model {

    private static final String ID_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RkNjM5NzhlODRlMjA5MjI4M2U5Y2QwNmU5ZWY0YmMyMjhiYjlmMjIyMmUxN2VlMzgzYjFjOWQ5N2E4YTAifX19";
    private static final String SHAPE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2IyYjVkNDhlNTc1Nzc1NjNhY2EzMTczNTUxOWNiNjIyMjE5YmMwNThiMWYzNDY0OGI2N2I4ZTcxYmMwZmEifX19";
    private static final String COOLDOWN_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMyYzYxNzE1MzJhMmE4N2YwZWViMjhlZGQwMTA4MzNmMzNmMGFlNjg0MWE1MjRlMWI1MjAwYTM1ZDM4NTA1MCJ9fX0=";
    private static final String SORTER_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzIxNmQxN2RlNDJiZDA5NzY2OWI4ZTA5ZThlNjJkZjhiZjc4MzdkMzk1OTc1NDk2ZTYzNmZkYTRmYTk1ZjNkIn19fQ==========";

    protected ElytraBooster plugin;
    protected int spawnTaskNumber;

    @Serializable(headTexture = ID_HEAD)
    private String id;
    @Serializable(headTexture = SHAPE_HEAD)
    private Volume volume;
    @Serializable(headTexture = COOLDOWN_HEAD)
    private int cooldown;
    @Serializable(headTexture = SORTER_HEAD)
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
