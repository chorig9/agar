package com.game.agarserver.communication;

import com.game.agar.shared.Connection;

import java.util.List;

/**
 * Created by Wojtas on 2017-04-22.
 */
public class NetworkThread implements Runnable{

    private final List<Connection> connections;

    public NetworkThread(List<Connection> connections) {
        this.connections = connections;
    }

    @Override
    public void run() {

    }
}
