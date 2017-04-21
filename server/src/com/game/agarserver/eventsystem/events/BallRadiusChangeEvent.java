package com.game.agarserver.eventsystem.events;

import com.game.agarserver.logic.Ball;


public class BallRadiusChangeEvent extends Event{

    private final Ball ball;
    private final double newRadius;

    public BallRadiusChangeEvent(Ball ball, double newRadius) {
        this.ball = ball;
        this.newRadius = newRadius;
    }

    public Ball getBall() {
        return ball;
    }

    public double getNewRadius() {
        return newRadius;
    }
}
