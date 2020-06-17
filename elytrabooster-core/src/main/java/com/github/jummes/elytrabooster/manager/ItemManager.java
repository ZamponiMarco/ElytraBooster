package com.github.jummes.elytrabooster.manager;

import com.github.jummes.elytrabooster.item.Item;
import com.github.jummes.libs.model.ModelManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@Getter
public class ItemManager extends ModelManager<Item> {

    private List<Item> items;

    public ItemManager(Class<Item> classObject, String databaseType, JavaPlugin plugin) {
        super(classObject, databaseType, plugin);
        this.items = database.loadObjects();
    }
}
