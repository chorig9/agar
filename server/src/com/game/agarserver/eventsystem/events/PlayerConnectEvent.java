package com.game.agarserver.eventsystem.events;


import com.game.agarserver.logic.User;
import com.game.agarserver.logic.World;

public class PlayerConnectEvent extends Event {
    public final User player;
    public final World world;

    public PlayerConnectEvent(User player, World world) {
        this.player = player;
        this.world = world;
    }
}
