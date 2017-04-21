package com.game.agarserver.eventsystem.events;

import com.game.agar.shared.Position;
import com.game.agarserver.logic.Ball;


public class BallMoveEvent extends Event {

    private final Ball ball;
    private final Position position;

    public BallMoveEvent(Ball ball, Position position) {
        this.ball = ball;
        this.position = position;
    }

    public Ball getBall() {
        return ball;
    }

    public Position getPosition() {
        return position;
    }
}
