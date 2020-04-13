package com.github.jummes.elytrabooster.manager;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.jummes.elytrabooster.spawner.Spawner;
import com.github.jummes.libs.model.ModelManager;

import lombok.Getter;

/**
 * Handles data of the spawners
 *
 * @author Marco
 */
@Getter
public class SpawnerManager extends ModelManager<Spawner> {

    private Set<Spawner> spawners;

    public SpawnerManager(Class<Spawner> classObject, String databaseType, JavaPlugin plugin) {
        super(classObject, databaseType, plugin);
        this.spawners = new HashSet<Spawner>(database.loadObjects());
    }

}
