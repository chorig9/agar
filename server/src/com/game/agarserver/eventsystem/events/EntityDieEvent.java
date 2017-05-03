package com.game.agarserver.eventsystem.events;

import com.game.agarserver.logic.Entity;

public class EntityDieEvent extends Event{
    public final Entity entity;

    public EntityDieEvent(Entity entity) {
        this.entity = entity;
    }
}
