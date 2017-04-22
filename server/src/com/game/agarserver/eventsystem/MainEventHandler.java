package com.game.agarserver.eventsystem;

import com.game.agarserver.eventsystem.events.Event;
import com.game.agarserver.eventsystem.events.FrameEvent;
import com.game.agarserver.logic.*;
import java.util.List;
import java.util.function.Consumer;

public class MainEventHandler implements Consumer<Event> {

    private Broadcaster broadcaster;

    public MainEventHandler(Broadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @Override
    public void accept(Event event){
        World world = ((FrameEvent)event).getWorld();
        world.getUsers().forEach(world::setMovingAnglesForUserBalls);
        world.getUsers().forEach(user -> handleUserBallCollisions(user, world));
        world.getBalls().forEach(Ball::move);
        broadcaster.sendQueuedPackets();
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
