package com.game.agar.communication;

import com.game.agar.entities.Ball;
import com.game.agar.entities.Entity;
import com.game.agar.entities.Food;
import com.game.agar.shared.Position;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class Handler {

    private List<Entity> entities;
    private Map<Long, Ball> playerBalls;
    private volatile boolean init = false;

    public Handler(List<Entity> entities, Map<Long, Ball> playerBalls) {
        this.entities = entities;
        this.playerBalls = playerBalls;
    }

    public void handleRequest(String request){
        JSONObject json = new JSONObject(request);

        String action = json.getString("action");
        switch (action) {
            case "add_food": {
                Position position = new Position( json.getDouble("x"), json.getDouble("y"));
                double radius = json.getDouble("radius");
                long entityId = json.getLong("id");
                entities.add(new Food(position, radius, entityId));
                break;
            }
            case "add_ball": {
                Position position = new Position( json.getDouble("x"), json.getDouble("y"));
                double radius = json.getDouble("radius");
                long id = json.getInt("id");
                Ball ball = new Ball(position, radius, id);
                entities.add(ball);
                playerBalls.put(id, ball);
                init = true;
                break;
            }
            case "remove": {
                long entityId = json.getLong("id");
                entities.removeIf(e -> e.getEntityId() == entityId);
                break;
            }
            case "move": {
                Long id = json.getLong("id");
                Ball destination = playerBalls.get(id);
                Position position = new Position((float) json.getDouble("x"), (float) json.getDouble("y"));
                destination.setPosition(position);
                break;
            }
            case "radius_change": {
                Long id = json.getLong("id");
                Ball destination = playerBalls.get(id);
                float new_radius = (float) json.getDouble("radius");
                destination.setRadius(new_radius);
                break;
            }
        }
    }

    public void waitForFirstBall(){
        while(!init);
    } //Genialne xD

}
