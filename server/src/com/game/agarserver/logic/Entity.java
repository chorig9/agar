package com.game.agarserver.logic;

import com.game.agarserver.tools.Vector;
import com.game.agarserver.eventsystem.events.EntityDieEvent;

public class Entity {

    Vector position;
    double radius;
    World world;
    long entityId;

    public Entity(World world, Vector position, double radius){
        this.world = world;
        this.position = position;
        this.radius = radius;
        this.entityId = createNextId();
    }

    public Entity(Vector position, double radius){
        this(null, position, radius);
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

    public Vector getPosition() {return position;}

    public double getRadius() {return radius;}

    public long getEntityId(){
        return entityId;
    }

    private static long currentId = 0;
    public static long createNextId(){
        currentId++;
        return currentId;
    }

}
