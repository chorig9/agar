package com.game.agarserver.communication;

import com.game.agar.shared.Position;
import com.game.agarserver.logic.Ball;
import com.game.agarserver.logic.User;
import com.game.agarserver.logic.World;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Consumer;

public class CommunicationListener implements Consumer<String> {
    private final User user;
    private final World world;

    public CommunicationListener(User user, World world) {
        this.user = user;
        this.world = world;
    }

    @Override
    public void accept(String request) {
        JSONObject json = new JSONObject(request);
        switch(json.getString("action")){
            case "mouse_update":
                synchronized (user) {
                    user.setTargetVector(new Position(json.getInt("x"), json.getInt("y")));
                }
                break;
            case "split_attempt":
                synchronized (user) {
                    List<Ball> createdBalls = user.splitBalls();
                    createdBalls.forEach(world::spawnEntity);
                    world.setMovingAnglesForUserBalls(user);
                }
        }
    }
}
