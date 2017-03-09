package com.game.agar.entities;

import com.game.agar.tools.FloatConverger;
import com.game.agar.tools.Position;
import com.game.agar.tools.PositionConverger;

public class Ball extends Entity {

    private long id;
    private double moveAngle;
    private PositionConverger position;
    private FloatConverger radius;

    public Ball(Position position, float radius, long id) {
        this.position = new PositionConverger(position, 100);
        this.radius = new FloatConverger(radius, 100);
        this.id = id;
    }

    public void setPosition(Position position){
        this.position.doConverge(position);
    }

    public void setRadius(float radius){
        this.radius.doConverge(radius);
    }

    @Override
    public Position getPosition() {
        return position.getValue();
    }

    @Override
    public float getRadius() {
        return radius.getValue();
    }

    public long getId(){ return id; }

    public float getWeight() {
        float radius = getRadius();
        return (float)(Math.PI * Math.pow(radius,2));
    }

    public double getMoveAngle(){ return moveAngle; }
    public void setMoveAngle(double moveAngle){ this.moveAngle = moveAngle; }
}
