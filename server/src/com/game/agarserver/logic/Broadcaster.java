package com.game.agarserver.logic;

import org.json.JSONObject;

import java.util.List;
import java.util.function.Consumer;

public class Broadcaster implements Consumer<JSONObject> {

    private List<User> users;

    public Broadcaster(List<User> users){
        this.users = users;
    }

    @Override
    public void accept(JSONObject packet) {
        for(User user : users){
            // TODO - check if user is in close enough to see this event

            user.sendPacket(packet);
        }
    }
}
