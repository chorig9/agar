package com.game.agarserver.logic;


import com.game.agarserver.communication.Connection;

public class User {

    public Player player;
    public Connection connection;

    public User(Player player, Connection connection){
        this.player = player;
        this.connection = connection;
    }

}
