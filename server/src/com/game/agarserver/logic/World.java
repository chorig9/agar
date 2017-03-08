package com.game.agarserver.logic;

import com.game.agarserver.communication.Connection;
import org.json.JSONObject;

import java.net.Socket;
import java.util.*;

public class World {

    private final int STARTING_RADIUS = 10;

    private List<Entity> balls = new ArrayList<>();
    private volatile List<User> users = new ArrayList<>();
    private Broadcaster broadcaster = new Broadcaster(users);
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
            Entity entity = new Entity(new Position(x, y), 7);
            entity.setListener(broadcaster);
            balls.add(entity);
        }
    }

    public void doStart(){
        running = true;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                users.forEach(user -> {
                    user.move(10);
                });
                handleCollisions();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 100);
    }

    public Position findFreePosition(){
        Random generator = new Random(); // TODO
        return new Position(generator.nextInt(width), generator.nextInt(height));
    }

    public void createNewPlayer(Socket socket){
        Connection connection = new Connection(socket);
        connection.start();

        User user = new User(findFreePosition(), STARTING_RADIUS, connection);
        user.setListener(broadcaster);

        connection.setCommunicationListener(request -> {
            System.out.println(request);
            JSONObject json = new JSONObject(request);
            switch(json.getString("action")){
                case "mouse_move":
                    user.setMovingAngle((float) json.getDouble("angle"));
                    return;
            }
        });

        new Thread(() -> {
            for(Entity ball : balls){
                user.sendPacket(PacketFactory.createAddBallPacket(ball));
            }
        }).start();

        users.add(user);
    }

    private void handleCollisions() {
        for(User user : users){

            balls.stream().filter(user::isCollision).forEach(user::handleCollision);
            balls.removeIf(user::isCollision);

            users.stream().
                    filter(anotherUser -> user != anotherUser & user.isCollision(anotherUser)).
                    forEach(user::handleCollision);
        }
    }

}
