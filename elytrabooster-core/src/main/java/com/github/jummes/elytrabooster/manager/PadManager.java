package com.github.jummes.elytrabooster.manager;

import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.pad.Pad;
import com.github.jummes.libs.model.ModelManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class PadManager extends ModelManager<Pad> {

    @Getter
    private List<Pad> pads;

    public PadManager(Class<Pad> classObject, String databaseType, JavaPlugin plugin) {
        super(classObject, databaseType, plugin);
        this.pads = database.loadObjects();
    }

    public void reloadData() {
        pads.forEach(Pad::stopBoosterTask);
        this.pads = database.loadObjects();
    }

}
