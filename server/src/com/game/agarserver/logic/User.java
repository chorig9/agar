package com.game.agarserver.logic;

import com.game.agarserver.communication.Connection;;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class User  {

    private static long next_id = 0;

    private Connection connection;
    private List<Ball> balls;
    private int score;
    private long id;

    public User(List<Ball> balls, Connection connection) {
        this.balls = balls;
        this.connection = connection;
        this.score = 0;
        this.id = (next_id++) % Long.MAX_VALUE;
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
