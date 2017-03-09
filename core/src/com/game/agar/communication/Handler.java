package com.game.agar.communication;

import com.game.agar.entities.Ball;
import com.game.agar.entities.Entity;
import com.game.agar.entities.Player;
import com.game.agar.tools.Position;

import java.util.List;
import org.json.JSONObject;

public class Handler {

    private List<Entity> entities;
    private Player player;

    public Handler(List<Entity> entities, Player player) {
        this.entities = entities;
        this.player = player;
    }

    public void handleRequest(String request){
        JSONObject json = new JSONObject(request);

        String action = json.getString("action");
        switch (action) {
            case "add": {
                Position position = new Position((float) json.getDouble("x"), (float) json.getDouble("y"));
                float radius = (float) json.getDouble("radius");
                entities.add(new Ball(position, radius));
                break;
            }
            case "remove": {
                Position position = new Position((float) json.getDouble("x"), (float) json.getDouble("y"));
                entities.removeIf(e -> e.getPosition().equals(position));
                break;
            }
            case "move": {
                Position position = new Position((float) json.getDouble("x"), (float) json.getDouble("y"));
                player.setPosition(position);
                break;
            }
            case "radius_change": {
                float new_radius = (float) json.getDouble("radius");
                player.setRadius(new_radius);
                break;
            }
        }
    }

}
