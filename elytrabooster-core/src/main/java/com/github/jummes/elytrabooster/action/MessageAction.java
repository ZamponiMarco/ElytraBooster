package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.targeter.PlayerTarget;
import com.github.jummes.elytrabooster.action.targeter.Target;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.util.MessageUtils;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class MessageAction extends AbstractAction {

    private static final String MESSAGE_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RkNjM5NzhlODRlMjA5MjI4M2U5Y2QwNmU5ZWY0YmMyMjhiYjlmMjIyMmUxN2VlMzgzYjFjOWQ5N2E4YTAifX19";

    @Serializable(headTexture = MESSAGE_HEAD, description = "gui.action.message.message")
    private String message;

    public MessageAction(){
        this("");
    }

    public static MessageAction deserialize(Map<String, Object> map) {
        String message = (String) map.get("message");
        return new MessageAction(message);
    }

    @Override
    public void execute(Target target) {
        ((PlayerTarget) target).getTarget().sendMessage(MessageUtils.color(message));
    }

    @Override
    public List<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(PlayerTarget.class);
    }

}
