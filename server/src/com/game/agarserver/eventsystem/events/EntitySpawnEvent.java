package com.game.agarserver.eventsystem.events;

import com.game.agarserver.logic.Entity;

/**
 * Created by Wojtas on 2017-04-21.
 */
public class EntitySpawnEvent extends Event{
    private final Entity entity;

    public EntitySpawnEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
