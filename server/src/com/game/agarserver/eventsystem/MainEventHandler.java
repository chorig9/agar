package com.game.agarserver.eventsystem;

import com.game.agarserver.eventsystem.events.FrameEndEvent;
import com.game.agarserver.eventsystem.events.FrameEvent;
import com.game.agarserver.logic.Ball;
import com.game.agarserver.logic.Entity;
import com.game.agarserver.logic.User;
import com.game.agarserver.logic.World;

import java.util.List;

public class MainEventHandler extends EventHandler{


    @SubscribeEvent
    public void everyFrame(FrameEvent event){
        World world = event.getWorld();
        world.getUsers().forEach(world::setMovingAnglesForUserBalls);
        world.getUsers().forEach(user -> handleUserBallCollisions(user, world));
        world.getBalls().forEach(Ball::move);
        event.getEventProcessor().issueEvent(new FrameEndEvent(event.getWorld()));
    }

    public void handleUserBallCollisions(User user, World world){
        user.getBalls().forEach(ball -> {
            List<Entity> food = world.getFood();
            List<Ball> balls = world.getBalls();
            synchronized (ball) {
                food.stream().filter(ball::isCollidingWith).forEach(ball::eatFood);
                food.removeIf(ball::isCollidingWith);

                balls.stream().
                        filter(anotherBall -> ball != anotherBall && ball.isCollidingWith(anotherBall)).
                        forEach(ball::handleCollision);
            }
        });
    }


}
