package com.game.agarserver.eventsystem;

import com.game.agarserver.communication.Broadcaster;
import com.game.agarserver.communication.NetworkManager;
import com.game.agarserver.eventsystem.events.*;
import com.game.agarserver.logic.*;


public class NetworkEventHandler extends EventHandler{

    private final NetworkManager networkManager;
    private final Broadcaster broadcaster;

    public NetworkEventHandler( NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.broadcaster = networkManager.getBroadcaster();
    }

    @SubscribeEvent
    public void onEntitySpawn(EntitySpawnEvent event){
        Entity entity = event.entity;
        if(entity instanceof Ball){  //TODO implement a way to send information about spawned entity of any type
            broadcaster.addPacketToSend(PacketFactory.createAddBallPacket((Ball)entity));
        }else if(entity instanceof Food){
            broadcaster.addPacketToSend(PacketFactory.createAddFoodPacket((Food)entity));
        }
    }

    @SubscribeEvent
    public void onEntityDie(EntityDieEvent event){
        broadcaster.addPacketToSend(PacketFactory.createRemovePacket(event.entity));
    }

    @SubscribeEvent
    public void onBallMove(BallMoveEvent event){
        broadcaster.addPacketToSend(PacketFactory.createPositionPacket(event.ball.getEntityId(), event.ball.getPosition()));
    }

    @SubscribeEvent
    public void onRadiusChange(BallRadiusChangeEvent event){
        broadcaster.addPacketToSend(PacketFactory.createRadiusPacket(event.ball.getEntityId(), event.ball.getRadius()));
    }

    @SubscribeEvent
    public void onFrameEnd(FrameEndEvent event){
        networkManager.addTask(broadcaster::sendQueuedPackets);
    }

    @SubscribeEvent
    public void onPlayerConnect(PlayerConnectEvent event){
        networkManager.addTask(() -> { //TODO replace this with a method which sends all the information about the world
            synchronized (event.world) {
                for (Entity entity : event.world.getFood()) {
                    event.player.sendPacket(PacketFactory.createAddFoodPacket(entity));
                }
            }
        });
    }
}
