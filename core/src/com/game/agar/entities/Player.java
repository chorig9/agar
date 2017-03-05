package com.game.agar.entities;

import com.badlogic.gdx.graphics.Color;
import com.game.agar.tools.FloatConverger;
import com.game.agar.tools.Position;
import com.game.agar.tools.PositionConverger;

public class Player extends Entity {

    private PositionConverger position;
    private FloatConverger radius;

    public Player(Position position, float radius) {
        this.position = new PositionConverger(position.copy(), position);
        this.radius = new FloatConverger(radius, radius);
    }

    public void setPosition(Position position){
        this.position.setConvergenceTo(position);
        this.position.doConvergeFully(50);
    }

    public void setRadius(float radius){
        this.radius.setConvergenceTo(radius);
        this.radius.doConvergeFully(100);
    }

    @Override
    public Position getPosition() {
        return position.getValue();
    }

    @Override
    public float getRadius() {
        return radius.getValue();
    }
}
