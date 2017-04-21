package com.game.agarserver.eventsystem.events;

import com.game.agarserver.logic.World;

/**
 * Created by Wojtas on 2017-04-14.
 */
public class FrameEvent extends Event {

    private final World world;

    public FrameEvent(World world) {
        super();
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

}
