package com.game.agarserver.logic;

import com.game.agar.shared.Vector;
import com.game.agar.shared.Connection;
import org.json.JSONObject;

import java.net.Socket;
import java.util.*;

public class World {

    private final List<Food> food = new ArrayList<>();
    private final List<Ball> balls = new ArrayList<>();
    private final List<Entity> entities = new ArrayList<>();
    private final List<User> users;
    private EventProcessor eventProcessor;
    private int width, height;

    public List<Food> getFood() {
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

    public List<Entity> getEntities() {
        return entities;
    }

    public World(List<User> users,EventProcessor eventProcessor, int width, int height){
        this.users = users;
        this.width = width;
        this.height = height;
        this.eventProcessor = eventProcessor;
    }
    public void initialize(){
        initializeBalls();
    }

    public void spawnEntity(Entity entity){
        entity.setWorld(this);//TODO
        eventProcessor.issueEvent(new EntitySpawnEvent(entity));
    }

    private void initializeBalls(){
        Random generator = new Random();
        Set<Vector> foodPositions = new HashSet<>();
        for(int i = 0; i < width * height / 20000; i++){
            int x = generator.nextInt(width);
            int y = generator.nextInt(height);
            if(!foodPositions.add(new Vector(x,y)))
                i--;
        }
        for(Vector position : foodPositions) {
            Food foodEntity = new Food(this, position, 30);
            spawnEntity(foodEntity);
        }
    }

    public Vector findFreePosition(){
        Random generator = new Random(); // TODO
        return new Vector(generator.nextInt(width), generator.nextInt(height));
    }

    synchronized public void addNewPlayer(User user){
        users.add(user);
        Ball initialBall = new Ball(this, findFreePosition(),100, user);
        this.spawnEntity(initialBall);
        eventProcessor.issueEvent(new PlayerConnectEvent(user, this));
    }

    public void setMovingAnglesForUserBalls(User user){//TODO move out from World
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
