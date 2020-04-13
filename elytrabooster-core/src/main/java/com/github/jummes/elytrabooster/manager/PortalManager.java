package com.github.jummes.elytrabooster.manager;

import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.jummes.elytrabooster.portal.Portal;
import com.github.jummes.libs.model.ModelManager;

import lombok.Getter;

/**
 * Handles data of the portals
 *
 * @author Marco
 */
public class PortalManager extends ModelManager<Portal> {

    @Getter
    private List<Portal> portals;

    public PortalManager(Class<Portal> classObject, String databaseType, JavaPlugin plugin) {
        super(classObject, databaseType, plugin);
        this.portals = database.loadObjects();
    }

}
