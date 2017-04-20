package com.game.agarserver.logic;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Broadcaster implements Consumer<JSONObject> {

    private List<User> users;
    private List<JSONObject> packets = new ArrayList<>();

    public Broadcaster(List<User> users){
        this.users = users;
    }

    synchronized public void addPacketToSend(JSONObject packet){
        packets.add(packet);
    }

    synchronized public void sendQueuedPackets(){
        packets.forEach(this::accept);
        packets.clear();
    }

    @Override
    public void accept(JSONObject packet) {
        for(User user : users){
            // TODO - check if user is in close enough to see this event

            user.sendPacket(packet);
        }
    }


}
