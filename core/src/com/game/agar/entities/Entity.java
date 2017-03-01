package com.game.agar.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.agar.tools.Position;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Entity {

    private static Random random = new Random();
    protected Position position;
    protected float radius;
    protected int weight;
    protected Color color;

    Entity(Position position, int weight){
        this.position = position;
        this.weight = weight;
        this.radius = (float)Math.sqrt(weight/Math.PI);
        float red = random.nextFloat();
        float green = random.nextFloat();
        float blue = random.nextFloat();
        Color randomColor = new Color(red,green,blue,1);
        this.color = randomColor;

    }

    public Position getPosition() {
        return position;
    }
    public float getRadius() { return radius; }
    public int getWeight() { return weight; }
    public Color getColor() { return color; }

    public void updateRadius(){
        radius = (float)Math.sqrt(weight/Math.PI);
    }

    public boolean isCollision(Entity collidingObject){
        double dx = Math.abs(this.position.x - collidingObject.getPosition().x);
        double dy = Math.abs(this.position.y - collidingObject.getPosition().y);
        double distance = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
        if(distance < this.radius + collidingObject.getRadius())
            return true;
        else
            return false;
    }

    public Entity getEatenEntity(Entity collidingObject){
        if(this.weight <= collidingObject.getWeight()) {
            collidingObject.eat(this);
            return this;
        }
        else
        {
            this.eat(collidingObject);
            return collidingObject;
        }
    }

    public void eat (Entity collidingObject){
        int massGained = collidingObject.getWeight();
        Timer timer = new Timer();
        TimerTask growth = new TimerTask() {
            int startWeight = weight;
            int gain = 1 + massGained/1000;
            @Override
            public void run() {
                if (weight == startWeight + massGained) {
                    cancel();
                    timer.cancel();
                    timer.purge();
                }
                else
                {
                    if(weight + gain <=  startWeight + massGained)
                        weight += gain;
                    else
                        weight = startWeight + massGained;
                    updateRadius();
                }
            }
        };
        timer.schedule(growth,0,1);
    }
}
