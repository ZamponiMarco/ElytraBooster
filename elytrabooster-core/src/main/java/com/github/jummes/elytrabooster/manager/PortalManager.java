package com.github.jummes.elytrabooster.manager;

import com.github.jummes.elytrabooster.portal.Portal;
import com.github.jummes.libs.model.ModelManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

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
