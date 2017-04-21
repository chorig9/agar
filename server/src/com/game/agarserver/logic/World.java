package com.game.agarserver.logic;

import com.game.agar.shared.Connection;
import com.game.agar.shared.Position;
import com.game.agarserver.communication.CommunicationListener;
import com.game.agarserver.eventsystem.EventProcessor;
import com.game.agarserver.eventsystem.events.EntitySpawnEvent;

import java.net.Socket;
import java.util.*;

public class World {

    private List<Entity> food = new ArrayList<>();
    private List<Ball> balls = new ArrayList<>();
    private volatile List<User> users = new ArrayList<>();
    private EventProcessor eventProcessor;
    private int width, height;

    private boolean running;

    public List<Entity> getFood() {
        return food;
    }

    public EventProcessor getEventProcessor() {
        return eventProcessor;
    }

    public void setEventProcessor(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public List<User> getUsers() {
        return users;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isRunning() {
        return running;
    }

    public World(int width, int height, EventProcessor eventProcessor){
        this.width = width;
        this.height = height;
        this.eventProcessor = eventProcessor;
        initializeBalls();
    }

    public void spawnEntity(Entity entity){
        entity.setWorld(this);//TODO
        eventProcessor.issueEvent(new EntitySpawnEvent(entity));
    }


    private void initializeBalls(){
        Random generator = new Random();
        Set<Position> foodPositions = new HashSet<>();
        for(int i = 0; i < width * height / 20000; i++){
            int x = generator.nextInt(width);
            int y = generator.nextInt(height);
            if(!foodPositions.add(new Position(x,y)))
                i--;
        }
        for(Position position : foodPositions) {
            Entity entity = new Entity(this, position, 30);
            food.add(entity);
        }
    }

    public Position findFreePosition(){
        Random generator = new Random(); // TODO
        return new Position(generator.nextInt(width), generator.nextInt(height));
    }

    synchronized public void createNewPlayer(Socket socket){
        Connection connection = new Connection(socket);
        connection.start();

        List<Ball> playerBalls = new ArrayList<>();
        User user = new User(playerBalls, connection);

        Ball initialBall = new Ball(this, findFreePosition(),100, user);
        playerBalls.add(initialBall);

        balls.addAll(playerBalls);

        playerBalls.forEach(ball -> user.sendPacket(PacketFactory.createAddBallPacket(ball)));

        connection.setCommunicationListener(new CommunicationListener(user, this));

        new Thread(() -> { //TODO
            synchronized (this) {
                for (Entity entity : food) {
                    user.sendPacket(PacketFactory.createAddFoodPacket(entity));
                }
            }
        }).start();

        users.add(user);
    }

    public void setMovingAnglesForUserBalls(User user){
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
