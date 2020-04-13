package com.github.jummes.elytrabooster.action;

import java.util.Map;

import com.github.jummes.elytrabooster.core.ElytraBooster;

public abstract class AbstractAction {

    public static final String ERROR_MSG = "Something wrong happened, check the actions you defined on boosters";

    protected ElytraBooster plugin;

    public AbstractAction(ElytraBooster plugin, Map<String, String> parameters) {
        this.plugin = plugin;
        try {
            parseParameters(parameters);
            executeAction();
        } catch (Exception e) {
            plugin.getLogger().warning(ERROR_MSG);
        }
    }

    protected abstract void parseParameters(Map<String, String> parameters);

    public abstract void executeAction();

}
