package com.game.agarserver.communication;


import com.game.agar.shared.Connection;

import java.util.ArrayList;
import java.util.List;


public class NetworkManager implements Runnable{

    private final List<Connection> connections;
    private final Broadcaster broadcaster;
    private final List<Runnable> tasks = new ArrayList<>();

    public NetworkManager(List<Connection> connections, Broadcaster broadcaster) {
        this.connections = connections;
        this.broadcaster = broadcaster;
    }
    public void addTask(Runnable task){
        synchronized (this){
            tasks.add(task);
            if(tasks.size() == 1){
                notify();
            }
        }
    }

    public Broadcaster getBroadcaster() {
        return broadcaster;
    }

    @Override
    public void run() {
        synchronized (this) {
            while (!Thread.interrupted()) {
                if (!tasks.isEmpty()) {
                    tasks.forEach(Runnable::run);
                    tasks.clear();
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }

}
