package com.game.agarserver.logic;

import com.game.agarserver.communication.Connection;
import org.json.JSONObject;

import java.net.Socket;
import java.util.*;

public class World {

    private final int STARTING_RADIUS = 10;

    private List<Entity> food = new ArrayList<>();
    private List<Ball> balls = new ArrayList<>();
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
            food.add(entity);
        }
    }

    public void doStart(){
        running = true;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                users.forEach(user -> {
                    for (Ball ball:user.getBalls()) {
                        food.stream().filter(ball::isCollision).forEach(ball::handleCollision);
                        food.removeIf(ball::isCollision);

                        balls.stream().
                                filter(anotherBall -> ball != anotherBall & ball.isCollision(anotherBall)).
                                forEach(ball::handleCollision);

                        ball.move();
                    }
                });
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

        List<Ball> playerBalls = new ArrayList<>();
        User user = new User(playerBalls,connection);

        Ball initialBall = new Ball(findFreePosition(),STARTING_RADIUS,user.getId());
        playerBalls.add(initialBall);
        balls.add(initialBall);
        Position second = new Position(initialBall.position.x-100,initialBall.position.y-100);
        initialBall = new Ball(second,STARTING_RADIUS,user.getId());
        playerBalls.add(initialBall);
        balls.add(initialBall);
        playerBalls.forEach(ball->ball.setListener(broadcaster));

        connection.setCommunicationListener(request -> {
            System.out.println(request);
            JSONObject json = new JSONObject(request);
            long destinationId = json.getLong("id");
            Ball destinationBall = null;
            for(Ball ball : playerBalls) {
                if(destinationId == ball.getId())
                    destinationBall = ball;
            }
            switch(json.getString("action")){
                case "move_angle_update":
                    destinationBall.setMoveAngle((float) json.getDouble("angle"));
                    return;
            }

        });

        new Thread(() -> {
            for(Entity entity : food){
                user.sendPacket(PacketFactory.createAddBallPacket(entity));
            }
        }).start();

        users.add(user);
    }


}
