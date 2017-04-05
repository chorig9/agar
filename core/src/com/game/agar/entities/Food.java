package com.game.agar.entities;

import com.game.agar.shared.Position;

public class Food extends Entity {

    private Position position;
    private double radius;

    public Food(Position position, double radius) {
        this.position = position;
        this.radius = radius;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public double getRadius() {
        return radius;
    }
}
