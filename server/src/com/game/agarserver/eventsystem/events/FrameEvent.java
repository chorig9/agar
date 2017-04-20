package com.game.agarserver.eventsystem.events;

import com.game.agarserver.logic.World;

/**
 * Created by Wojtas on 2017-04-14.
 */
public class FrameEvent extends Event {

    final World world;

    public World getWorld() {
        return world;
    }

    public FrameEvent(World world) {
        super();
        this.world = world;
    }
}
