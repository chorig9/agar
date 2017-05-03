package com.game.agarserver.eventsystem.events;

import com.game.agar.shared.Position;
import com.game.agarserver.logic.Ball;


public class BallMoveEvent extends Event {

    public final Ball ball;
    public final Position position;

    public BallMoveEvent(Ball ball, Position position) {
        this.ball = ball;
        this.position = position;
    }
}
