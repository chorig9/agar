package com.game.agarserver.logic;

import com.game.agar.shared.Position;
import org.json.JSONObject;

import java.util.function.Consumer;

public class Entity {

    Position position;
    double radius;
    Consumer<JSONObject> listener;

    public Entity(Position position, double radius){
        this.position = position;
        this.radius = radius;
    }

    public void setListener(Consumer<JSONObject> listener){
        this.listener = listener;
    }

    public void die(){
        listener.accept(PacketFactory.createRemovePacket(position));
    }

    public double getWeight(){
        return Math.PI * radius * radius;
    }

    public Position getPosition() {return position;}

    public double getRadius() {return radius;}

}
