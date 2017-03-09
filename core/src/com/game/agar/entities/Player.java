package com.game.agar.entities;

import com.badlogic.gdx.graphics.Color;
import com.game.agar.tools.FloatConverger;
import com.game.agar.tools.Position;
import com.game.agar.tools.PositionConverger;

public class Player extends Entity {

    private PositionConverger position;
    private FloatConverger radius;

    public Player(Position position, float radius) {
        this.position = new PositionConverger(position, 1000);
        this.radius = new FloatConverger(radius, 100);
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
}
