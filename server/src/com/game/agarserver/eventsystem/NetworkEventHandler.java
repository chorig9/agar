package com.game.agarserver.eventsystem;

import com.game.agarserver.communication.Broadcaster;
import com.game.agarserver.communication.NetworkManager;
import com.game.agarserver.eventsystem.events.*;
import com.game.agarserver.logic.Ball;
import com.game.agarserver.logic.Entity;
import com.game.agarserver.logic.PacketFactory;
import com.game.agarserver.logic.World;


public class NetworkEventHandler extends EventHandler{

    private final NetworkManager networkManager;
    private final Broadcaster broadcaster;

    public NetworkEventHandler( NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.broadcaster = networkManager.getBroadcaster();
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

    @SubscribeEvent
    public void onFrameEnd(FrameEndEvent event){
        networkManager.addTask(broadcaster::sendQueuedPackets);
    }

    @SubscribeEvent
    public void onPlayerConnect(PlayerConnectEvent event){
        final World world = event.getWorld();
        networkManager.addTask(() -> { //TODO replace this with a method which sends all the information about the world
            synchronized (world) {
                for (Entity entity : world.getFood()) {
                    event.getPlayer().sendPacket(PacketFactory.createAddFoodPacket(entity));
                }
            }
        });
    }
}
