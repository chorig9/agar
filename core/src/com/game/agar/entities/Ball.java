package com.game.agar.entities;

import com.game.agar.tools.Position;

public class Ball extends Entity {

    private Position position;
    private float radius;

    public Ball(Position position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public float getRadius() {
        return radius;
    }
}
