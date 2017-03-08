package com.game.agarserver.logic;

import com.game.agarserver.communication.Connection;
import org.json.JSONObject;

import java.io.IOException;

public class User extends Player{

    private Connection connection;

    public User(Position position, int radius, Connection connection) {
        super(position, radius);
        this.connection = connection;
    }

    public void sendPacket(JSONObject json){
        try {
            connection.send(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void die() {
        connection.end();
        super.die();
    }
}
