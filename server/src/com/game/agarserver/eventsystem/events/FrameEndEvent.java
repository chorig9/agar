package com.game.agarserver.eventsystem.events;

import com.game.agarserver.logic.World;

/**
 * Created by Wojtas on 2017-04-22.
 */
public class FrameEndEvent extends Event {

    private final World world;

    public FrameEndEvent(World world) {
        super();
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
