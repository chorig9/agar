package com.game.agarserver.eventsystem.events;

import com.game.agarserver.logic.World;

public class FrameEndEvent extends Event {

    public final World world;

    public FrameEndEvent(World world) {
        super();
        this.world = world;
    }
}
