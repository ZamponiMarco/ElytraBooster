package com.github.jummes.elytrabooster.manager;

import com.github.jummes.elytrabooster.pad.Pad;
import com.github.jummes.libs.model.ModelManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public class PadManager extends ModelManager<Pad> {

    @Getter
    private List<Pad> pads;

    public PadManager(Class<Pad> classObject, String databaseType, JavaPlugin plugin, Map<String, Object> args) {
        super(classObject, databaseType, plugin, args);
        this.pads = database.loadObjects();
    }

    public void reloadData() {
        pads.forEach(Pad::stopBoosterTask);
        this.pads = database.loadObjects();
    }

}
