package com.game.agar.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.agar.tools.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player extends Entity {

    private double movingDirectionAngle;

    public Player(Position position, int weight) {
        super(position, weight);
        color = Color.RED;
    }

    public void setMovingDirection(double angle) {
        this.movingDirectionAngle = angle;
    }

    public void move(double translation) {
        position.x += translation * Math.cos(movingDirectionAngle);
        position.y += translation * Math.sin(movingDirectionAngle);
    }

}
