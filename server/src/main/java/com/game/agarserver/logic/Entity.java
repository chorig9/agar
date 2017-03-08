package com.game.agarserver.logic;

public class Entity {

    Position position;
    float radius;

    public Entity(Position position, float radius){
        this.position = position;
        this.radius = radius;
    }

    public float getWeight(){
        return (float) Math.PI * radius * radius;
    }

}
