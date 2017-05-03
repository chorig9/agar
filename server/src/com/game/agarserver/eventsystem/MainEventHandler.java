package com.game.agarserver.eventsystem;

import com.game.agarserver.eventsystem.events.EntityDieEvent;
import com.game.agarserver.eventsystem.events.EntitySpawnEvent;
import com.game.agarserver.eventsystem.events.FrameEndEvent;
import com.game.agarserver.eventsystem.events.FrameEvent;
import com.game.agarserver.logic.*;

import java.util.List;

public class MainEventHandler extends EventHandler{


    @SubscribeEvent
    public void everyFrame(FrameEvent event){
        World world = event.world;
        world.getUsers().forEach(world::setMovingAnglesForUserBalls);
        world.getUsers().forEach(user -> handleUserBallCollisions(user, world));
        world.getBalls().forEach(Ball::move);
        event.getEventProcessor().issueEvent(new FrameEndEvent(world));
        if(world.getUsers().size() > 0) {
            System.out.println(world.getFood().size());
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(EntitySpawnEvent event){
        Entity entity = event.entity;
        World world = entity.getWorld();

        synchronized (world) {
            world.getEntities().add(entity);
            if (entity instanceof Food) {
                world.getFood().add((Food) entity);
            }else if(entity instanceof Ball){
                Ball ball = (Ball) entity;
                ball.getOwner().getBalls().add(ball);
                world.getBalls().add(ball);
            }
        }

    }

    @SubscribeEvent
    private void onEntityDie(EntityDieEvent event){
        Entity entity = event.entity;
        World world = entity.getWorld();

        synchronized (world){
            world.getEntities().remove(entity);
            if (entity instanceof Food) {
                //world.getFood().remove(entity);
                //TODO implement removing entities from lists - must be performed after collision handling
            }else if(entity instanceof Ball){
                Ball ball = (Ball) entity;
                ball.getOwner().getBalls().remove(ball);
                //world.getBalls().remove(ball);
                //TODO same as above
            }
        }
    }

    public void handleUserBallCollisions(User user, World world){
        List<Food> food = world.getFood();
        List<Ball> balls = world.getBalls();

        user.getBalls().forEach(ball -> {
            food.forEach(ball::checkAndHandleEating);
            food.removeIf(ball::canEat);
        });

        for(int i = 0; i < user.getBalls().size(); i++) {
            user.getBalls().forEach(ball -> {
                balls.forEach(ball::checkAndHandleCollision);
            });
        }
    }
}
