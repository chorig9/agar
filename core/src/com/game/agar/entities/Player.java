package com.game.agar.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.agar.tools.Position;

import java.util.*;

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

    public void eat (Entity collidingObject){
        int massGained = collidingObject.getWeight();
        Timer timer = new Timer();
        TimerTask growth = new TimerTask() {
            int targetWeight = weight + massGained ;
            int gain = 1 + massGained/1000;
            @Override
            public void run() {
                if (weight == targetWeight) {
                    cancel();
                    timer.cancel();
                    timer.purge();
                }
                else
                {
                    if(weight + gain <=  targetWeight)
                        weight += gain;
                    else
                        weight = targetWeight;
                    updateRadius();
                }
            }
        };
        timer.schedule(growth,0,1);
    }

}
