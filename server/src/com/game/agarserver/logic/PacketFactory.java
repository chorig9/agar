package com.game.agarserver.logic;

import org.json.JSONObject;

public class PacketFactory {

    static JSONObject createPositionPacket(long id, Position position){
        JSONObject json = new JSONObject();
        json.put("action", "move");
        json.put("id", id);
        json.put("x", position.x);
        json.put("y", position.y);
        return json;
    }

    static JSONObject createRadiusPacket(long id, float radius){
        JSONObject json = new JSONObject();
        json.put("action", "radius_change");
        json.put("id", id);
        json.put("radius", radius);
        return json;
    }

    static JSONObject createAddBallPacket(Entity ball){
        JSONObject json = new JSONObject();
        json.put("action", "add");
        json.put("x", ball.position.x);
        json.put("y", ball.position.y);
        json.put("radius", ball.radius);
        return json;
    }

    static JSONObject createRemovePacket(Position position){
        JSONObject json = new JSONObject();
        json.put("action", "remove");
        json.put("x", position.x);
        json.put("y", position.y);
        return json;
    }

    static JSONObject requestMoveAngle(long id){
        JSONObject json = new JSONObject();
        json.put("action", "request");
        json.put("id", id);
        json.put("key", "move_angle");
        return json;
    }

}
