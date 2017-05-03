package com.game.agarserver.eventsystem.events;

import com.game.agarserver.logic.Ball;


public class BallRadiusChangeEvent extends Event{

    public final Ball ball;
    public final double newRadius;

    public BallRadiusChangeEvent(Ball ball, double newRadius) {
        this.ball = ball;
        this.newRadius = newRadius;
    }
}
