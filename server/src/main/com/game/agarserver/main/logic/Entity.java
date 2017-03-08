package com.game.agarserver.main.logic;

import org.json.JSONObject;

import java.util.function.Consumer;

public class Entity {

    Position position;
    float radius;
    Consumer<JSONObject> listener;

    public Entity(Position position, float radius){
        this.position = position;
        this.radius = radius;
    }

    public void setListener(Consumer<JSONObject> listener){
        this.listener = listener;
    }

    public void die(){
        listener.accept(PacketFactory.createRemovePacket(position));
    }

    public float getWeight(){
        return (float) Math.PI * radius * radius;
    }

}
