package com.game.agarserver.logic;

import com.game.agarserver.tools.Vector;
import com.game.agar.shared.Connection;
import org.json.JSONObject;

import java.net.Socket;
import java.util.*;

public class World {

    private final List<Entity> food = new ArrayList<>();
    private final List<Ball> balls = new ArrayList<>();
    private volatile List<User> users = new ArrayList<>();
    private final Broadcaster broadcaster = new Broadcaster(users);
    private int width, height;

    private boolean running;

    public World(int width, int height){
        this.width = width;
        this.height = height;

        initializeBalls();
    }

    private void initializeBalls(){
        Random generator = new Random();
        Set<Vector> foodPositions = new HashSet<Vector>();
        for(int i = 0; i < width * height / 20000; i++){
            int x = generator.nextInt(width);
            int y = generator.nextInt(height);
            if(!foodPositions.add(new Vector(x,y)))
                i--;
        }
        for(Vector position : foodPositions) {
            Entity entity = new Entity(position, 30);
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
                    food.forEach(ball::checkAndHandleEating);
                    food.removeIf(ball::canEat);
        });

        for(int i = 0; i < 100; i++) {
            user.getBalls().forEach(ball -> {
                balls.forEach(ball::checkAndHandleCollision);
            });
        }
    }

    public Vector findFreePosition(){
        Random generator = new Random(); // TODO
        return new Vector(generator.nextInt(width), generator.nextInt(height));
    }

    public void createNewPlayer(Socket socket){
        Connection connection = new Connection(socket);
        connection.start();

        List<Ball> playerBalls = new ArrayList<>();
        User user = new User(playerBalls,connection);

        Ball initialBall = new Ball(findFreePosition(),100,user.getId());
        playerBalls.add(initialBall);

        balls.addAll(playerBalls);

        playerBalls.forEach(ball -> ball.setListener(broadcaster));
        playerBalls.forEach(ball -> user.sendPacket(PacketFactory.createAddBallPacket(ball)));

        connection.setCommunicationListener(request -> {
            JSONObject json = new JSONObject(request);
            switch(json.getString("action")){
                case "mouse_update":
                    synchronized (user) {
                        user.setTargetVector(new Vector(json.getInt("x"), json.getInt("y")));
                    }
                    break;
                case "split_attempt":
                    synchronized (user) {
                        List<Ball> createdBalls = user.splitBalls();
                        balls.addAll(createdBalls);
                        playerBalls.addAll(createdBalls);
                        setMovingAnglesForUserBalls(user);
                        createdBalls.forEach(ball->ball.setListener(broadcaster));
                        createdBalls.forEach(ball->user.sendPacket(PacketFactory.createAddBallPacket(ball)));
                    }
            }
        });

        new Thread(() -> {
            synchronized (this) {
                for (Entity entity : food) {
                    user.sendPacket(PacketFactory.createAddFoodPacket(entity));
                }
            }
        }).start();

        users.add(user);
    }

    private void setMovingAnglesForUserBalls(User user){
        synchronized (user) {
            Ball centerBall = user.getBalls().stream().max((lhs, rhs) -> Double.compare(lhs.radius, rhs.radius)).get();
            Vector targetPosition = new Vector(centerBall.getPosition().x + user.getTargetVector().x,
                    centerBall.getPosition().y + user.getTargetVector().y);

            user.getBalls().forEach(ball -> {
                double angle = Math.atan2(targetPosition.y - ball.position.y, targetPosition.x - ball.position.x);
                ball.setMoveAngle(angle);
            });
        }
    }

}
