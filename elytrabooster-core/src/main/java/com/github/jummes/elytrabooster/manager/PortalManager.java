package com.github.jummes.elytrabooster.manager;

import com.github.jummes.elytrabooster.portal.Portal;
import com.github.jummes.libs.model.ModelManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

/**
 * Handles data of the portals
 *
 * @author Marco
 */
public class PortalManager extends ModelManager<Portal> {

    @Getter
    private List<Portal> portals;

    public PortalManager(Class<Portal> classObject, String databaseType, JavaPlugin plugin, Map<String, Object> args) {
        super(classObject, databaseType, plugin, args);
        this.portals = database.loadObjects();
    }

    public void reloadData() {
        portals.forEach(Portal::stopBoosterTask);
        this.portals = database.loadObjects();
    }


}
