package com.game.agarserver.eventsystem.events;

import com.game.agarserver.logic.World;

public class FrameEvent extends Event {

    public final World world;

    public FrameEvent(World world) {
        super();
        this.world = world;
    }

}
