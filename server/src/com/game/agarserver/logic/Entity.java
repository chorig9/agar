package com.game.agarserver.logic;

import com.game.agar.shared.Position;
import com.game.agarserver.eventsystem.events.EntityDieEvent;

public class Entity {

    Position position;
    double radius;
    World world;

    public Entity(Position position, double radius){
        this.position = position;
        this.radius = radius;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public void die(){
        world.getEventProcessor().issueEvent(new EntityDieEvent(this));
    }

    public double getWeight(){
        return Math.PI * radius * radius;
    }

    public Position getPosition() {return position;}

    public double getRadius() {return radius;}

}
