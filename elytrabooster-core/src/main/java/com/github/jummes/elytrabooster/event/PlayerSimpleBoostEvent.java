package com.github.jummes.elytrabooster.event;

import com.github.jummes.elytrabooster.action.targeter.LocationTarget;
import com.github.jummes.elytrabooster.action.targeter.PlayerTarget;
import com.github.jummes.elytrabooster.boost.SimpleBoost;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.libs.util.MessageUtils;
import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class PlayerSimpleBoostEvent extends PlayerBoostEvent {

    @Getter
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Player player;
    private boolean cancelled;

    public PlayerSimpleBoostEvent(Player player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        cancelled = isCancelled;
    }

}
