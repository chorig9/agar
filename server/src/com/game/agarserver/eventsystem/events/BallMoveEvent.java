package com.game.agarserver.eventsystem.events;

import com.game.agar.shared.Position;
import com.game.agarserver.logic.Ball;


public class BallMoveEvent extends Event {

    private Ball ball;
    private Position position;

    public BallMoveEvent(Ball ball, Position position) {
        this.ball = ball;
    }

    public Ball getBall() {
        return ball;
    }

    public Position getPosition() {
        return position;
    }
}
