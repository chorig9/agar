package com.game.agarserver.eventsystem.events;


import com.game.agarserver.logic.User;
import com.game.agarserver.logic.World;

public class PlayerConnectEvent extends Event {
    private final User player;
    private final World world;

    public PlayerConnectEvent(User player, World world) {
        this.player = player;
        this.world = world;
    }

    public User getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }
}
