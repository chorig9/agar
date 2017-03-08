package com.game.agarserver.logic;


import com.game.agarserver.communication.Connection;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class World {

    private final int STARTING_RADIUS = 10;

    private List<Entity> balls = new ArrayList<>();
    private volatile List<User> users = new ArrayList<>();
    private int width, height;

    private boolean running;

    public World(int width, int height){
        this.width = width;
        this.height = height;

        initializeBalls();
    }

    private void initializeBalls(){
        Random generator = new Random();
        for(int i = 0; i < width * height / 10000; i++){
            int x = generator.nextInt(width);
            int y = generator.nextInt(height);
            balls.add(new Entity(new Position(x, y), 7));
        }
    }

    public void doStart(){
        running = true;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                users.forEach(user -> {
                    user.player.move(1);
                    try {
                        user.connection.send(PacketFactory.createPositionPacket(user.player.position));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                handleCollisions();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 50);
    }

    public Position findFreePosition(){
        Random generator = new Random(); // TODO
        return new Position(generator.nextInt(width), generator.nextInt(height));
    }

    public void createNewPlayer(Socket socket){
        Connection connection = new Connection(socket);
        connection.start();

        Player player = new Player(findFreePosition(), STARTING_RADIUS);
        User user = new User(player, connection);

        connection.setCommunicationListener(request -> {
            System.out.println(request);
            JSONObject json = new JSONObject(request);
            switch(json.getString("action")){
                case "mouse_move":
                    user.player.setMovingAngle((float) json.getDouble("angle"));
                    return;
            }
        });

        new Thread(() -> {
            for(Entity ball : balls){
                try {
                    user.connection.send(PacketFactory.createAddPacket(ball));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        users.add(user);
    }

    private void handleCollisions() {
        List<User> dead = new ArrayList<>();



        for(User user : users){

            balls.stream().filter(ball -> user.player.isCollision(ball)).forEach(ball -> {
                user.player.updateRadius(ball.getWeight());

                try {
                    user.connection.send(PacketFactory.createRadiusPacket(user.player.radius));
                    user.connection.send(PacketFactory.createRemovePacket(ball.position));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            balls.removeIf(ball -> user.player.isCollision(ball));

            for(User anotherUser : users){

                Player p1 = user.player;
                Player p2 = anotherUser.player;

                if(p1 != p2 & p1.isCollision(p2)){
                    if(p1.getWeight() > p2.getWeight()) {
                        p1.updateRadius(p2.getWeight());

                        try {
                            user.connection.send(PacketFactory.createRadiusPacket(p1.radius));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        dead.add(anotherUser);
                    }
                    else{
                        p2.updateRadius(p1.getWeight());
                        dead.add(user);
                    }
                }
            }
        }

        dead.forEach(user -> {
            // TODO - send info to appropriate players
            // TODO - or create some kind of listener (if detects that player is removed, notifies other players)
            user.connection.end();
            users.remove(user);
        });
    }

}
