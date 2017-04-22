package com.game.agarserver.communication;


import com.game.agarserver.eventsystem.MainEventHandler;
import com.game.agarserver.eventsystem.events.FrameEvent;
import com.game.agarserver.logic.Broadcaster;
import com.game.agarserver.logic.World;

public class GameThread implements Runnable{

    private World world;
    private Broadcaster broadcaster;

    public GameThread(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            world.getEventProcessor().issueEvent(new FrameEvent(world));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
