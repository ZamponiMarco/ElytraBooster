package com.github.jummes.elytrabooster.manager;

import com.github.jummes.elytrabooster.spawner.Spawner;
import com.github.jummes.libs.model.ModelManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

/**
 * Handles data of the spawners
 *
 * @author Marco
 */
@Getter
public class SpawnerManager extends ModelManager<Spawner> {

    private List<Spawner> spawners;

    public SpawnerManager(Class<Spawner> classObject, String databaseType, JavaPlugin plugin, Map<String, Object> args) {
        super(classObject, databaseType, plugin, args);
        this.spawners = database.loadObjects();
    }

    public void reloadData() {
        spawners.forEach(Spawner::stopBoosterTask);
        this.spawners = database.loadObjects();
    }


}
