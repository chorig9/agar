package com.game.agar.entities;

import com.game.agar.shared.Position;
import com.game.agar.tools.DoubleConverger;
import com.game.agar.tools.PositionConverger;

public class Ball extends Entity {

    private long id;
    private PositionConverger position;
    private DoubleConverger radius;

    public Ball(Position position, double radius, long id) {
        this.position = new PositionConverger(position, 100);
        this.radius = new DoubleConverger(radius, 100);
        this.id = id;
    }

    public void setPosition(Position position){
        this.position.doConverge(position);
    }

    public void setRadius(double radius){
        this.radius.doConverge(radius);
    }

    @Override
    public Position getPosition() {
        return position.getValue();
    }

    @Override
    public double getRadius() {
        return radius.getValue();
    }

    public long getId(){ return id; }

    public float getWeight() {
        double radius = getRadius();
        return (float)(Math.PI * Math.pow(radius,2));
    }
}
