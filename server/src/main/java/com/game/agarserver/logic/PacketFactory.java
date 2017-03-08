package com.game.agarserver.logic;

import org.json.JSONObject;

public class PacketFactory {

    static String createPositionPacket(Position position){
        JSONObject json = new JSONObject();
        json.put("action", "move");
        json.put("x", position.x);
        json.put("y", position.y);
        return json.toString();
    }

    static String createRadiusPacket(float radius){
        JSONObject json = new JSONObject();
        json.put("action", "radius_change");
        json.put("radius", radius);
        return json.toString();
    }

    static String createAddPacket(Entity ball){
        JSONObject json = new JSONObject();
        json.put("action", "add");
        json.put("x", ball.position.x);
        json.put("y", ball.position.y);
        json.put("radius", ball.radius);
        return json.toString();
    }

    static String createRemovePacket(Position position){
        JSONObject json = new JSONObject();
        json.put("action", "remove");
        json.put("x", position.x);
        json.put("y", position.y);
        return json.toString();
    }

}
