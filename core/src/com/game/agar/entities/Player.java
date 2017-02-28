package com.game.agar.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.agar.tools.Position;

public class Player extends Entity {

    private double movingDirectionAngle;

    public Player(Position position) {

        super(position, null);

        Pixmap pixmap = new Pixmap(32,32, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fillCircle(16,16,12);
        Texture texture = new Texture(pixmap);

        sprite = new Sprite(texture);
    }

    public void setMovingDirection(double angle){
        this.movingDirectionAngle = angle;
    }

    public void move(double translation){
        position.x += translation * Math.cos(movingDirectionAngle);
        position.y += translation * Math.sin(movingDirectionAngle);
    }
}
