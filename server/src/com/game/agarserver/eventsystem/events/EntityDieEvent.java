package com.game.agarserver.eventsystem.events;

import com.game.agarserver.logic.Entity;

/**
 * Created by Wojtas on 2017-04-19.
 */
public class EntityDieEvent extends Event{
    private Entity entity;

    public EntityDieEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
