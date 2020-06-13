package com.github.jummes.elytrabooster.action.targeter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
public class PlayerTarget implements Target {

    private final Player target;

}
