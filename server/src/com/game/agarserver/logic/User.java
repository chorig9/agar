package com.game.agarserver.logic;

import com.game.agar.shared.Connection;
import com.game.agar.shared.Position;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class User  {

    private static long next_id = 0;

    private Connection connection;
    private List<Ball> balls;
    private Position targetVector;
    private int score;
    private long id;

    public User(List<Ball> balls, Connection connection) {
        this.balls = balls;
        this.connection = connection;
        this.score = 0;
        this.id = (next_id++) % Long.MAX_VALUE;
        this.targetVector = new Position(0,0);
    }

    public void setTargetVector(Position position){
        targetVector = position;
    }

    public Position getTargetVector(){
        return targetVector;
    }

    public void sendPacket(JSONObject json){
        try {
            connection.send(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Ball> getBalls(){   return balls;   }
    public long getId() {   return id;  }

    public void endConnection() {
        connection.end();
    }
}
