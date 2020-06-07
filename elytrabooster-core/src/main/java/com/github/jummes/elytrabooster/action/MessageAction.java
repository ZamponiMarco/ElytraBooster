package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.PlayerTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.util.MessageUtils;
import com.google.common.collect.Lists;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class MessageAction extends AbstractAction {

    private Player target;
    private String message;

    @Override
    public void execute(Target target) {
        this.target.sendMessage(MessageUtils.color(message));
    }

    @Override
    public List<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(PlayerTarget.class);
    }

}
