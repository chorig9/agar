package com.game.agarserver.eventsystem.events;

import com.game.agarserver.logic.Entity;

public class EntitySpawnEvent extends Event{
    public final Entity entity;

    public EntitySpawnEvent(Entity entity) {
        this.entity = entity;
    }

}
