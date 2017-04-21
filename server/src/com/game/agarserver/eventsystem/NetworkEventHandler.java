package com.game.agarserver.eventsystem;

import com.game.agarserver.eventsystem.events.BallMoveEvent;
import com.game.agarserver.eventsystem.events.BallRadiusChangeEvent;
import com.game.agarserver.eventsystem.events.EntityDieEvent;
import com.game.agarserver.logic.Ball;
import com.game.agarserver.logic.Broadcaster;
import com.game.agarserver.logic.PacketFactory;


public class NetworkEventHandler extends EventHandler{

    private Broadcaster broadcaster;

    public NetworkEventHandler(Broadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @SubscribeEvent
    public void onEntityDie(EntityDieEvent event){
        broadcaster.addPacketToSend(PacketFactory.createRemovePacket(event.getEntity()));
    }

    @SubscribeEvent
    public void onBallMove(BallMoveEvent event){
        Ball ball = event.getBall();
        broadcaster.addPacketToSend(PacketFactory.createPositionPacket(ball.getEntityId(), ball.getPosition()));
    }

    @SubscribeEvent
    public void onRadiusChange(BallRadiusChangeEvent event){
        Ball ball = event.getBall();
        broadcaster.addPacketToSend(PacketFactory.createRadiusPacket(ball.getEntityId(), ball.getRadius()));
    }
}
