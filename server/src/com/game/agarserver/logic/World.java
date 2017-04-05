package com.game.agarserver.logic;

import com.game.agar.shared.Position;
import com.game.agar.shared.Connection;
import org.json.JSONObject;

import java.net.Socket;
import java.util.*;

public class World {

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
            Entity entity = new Entity(new Position(x, y), 30);
            entity.setListener(broadcaster);
            food.add(entity);
        }
    }

    public void doStart(){
        running = true;

        new Thread(()->{
            while(running){
                users.forEach(this::setMovingAnglesForUserBalls);
                users.forEach(this::handleUserBallCollisions);
                balls.forEach(Ball::move);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void handleUserBallCollisions(User user){
        user.getBalls().forEach(ball -> {
            synchronized (ball) {
                food.stream().filter(ball::isCollidingWith).forEach(ball::eatFood);
                food.removeIf(ball::isCollidingWith);

                balls.stream().
                        filter(anotherBall -> ball != anotherBall && ball.isCollidingWith(anotherBall)).
                        forEach(ball::handleCollision);
            }
        });
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

        Ball initialBall = new Ball(findFreePosition(),100,user.getId());
        playerBalls.add(initialBall);

        Position second = new Position(initialBall.position.x-300,initialBall.position.y-300);
        initialBall = new Ball(second, 70,user.getId());
        playerBalls.add(initialBall);

        Position third = new Position(initialBall.position.x+600,initialBall.position.y+600);
        initialBall = new Ball(third, 120,user.getId());
        playerBalls.add(initialBall);

        balls.addAll(playerBalls);

        playerBalls.forEach(ball -> ball.setListener(broadcaster));
        playerBalls.forEach(ball -> user.sendPacket(PacketFactory.createAddBallPacket(ball)));

        connection.setCommunicationListener(request -> {
            JSONObject json = new JSONObject(request);
            switch(json.getString("action")){
                case "mouse_update":
                    synchronized (user) {
                        user.setTargetVector(new Position(json.getInt("x"), json.getInt("y")));
                    }
            }
        });

        new Thread(() -> {
            for(Entity entity : food){
                user.sendPacket(PacketFactory.createAddFoodPacket(entity));
            }
        }).start();

        users.add(user);
    }

    private void setMovingAnglesForUserBalls(User user){
        synchronized (user) {
            Ball centerBall = user.getBalls().stream().max((lhs, rhs) -> Double.compare(lhs.radius, rhs.radius)).get();
            Position targetPosition = new Position(centerBall.getPosition().x + user.getTargetVector().x,
                    centerBall.getPosition().y + user.getTargetVector().y);

            user.getBalls().forEach(ball -> {
                double angle = Math.atan2(targetPosition.y - ball.position.y, targetPosition.x - ball.position.x);
                ball.setMoveAngle(angle);
            });
        }
    }

}
