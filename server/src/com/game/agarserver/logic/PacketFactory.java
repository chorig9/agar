package com.game.agarserver.logic;

import com.game.agar.shared.Position;
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

    static JSONObject createRadiusPacket(long id, double radius){
        JSONObject json = new JSONObject();
        json.put("action", "radius_change");
        json.put("id", id);
        json.put("radius", radius);
        return json;
    }

    static JSONObject createAddFoodPacket(Entity food){
        JSONObject json = new JSONObject();
        json.put("action", "add_food");
        json.put("x", food.position.x);
        json.put("y", food.position.y);
        json.put("radius", food.radius);
        return json;
    }

    static JSONObject createAddBallPacket(Ball ball){
        JSONObject json = new JSONObject();
        json.put("action", "add_ball");
        json.put("x", ball.position.x);
        json.put("y", ball.position.y);
        json.put("radius", ball.radius);
        json.put("id", ball.getId());
        return json;
    }

    static JSONObject createRemovePacket(Position position){
        JSONObject json = new JSONObject();
        json.put("action", "remove");
        json.put("x", position.x);
        json.put("y", position.y);
        return json;
    }

}
