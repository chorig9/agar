package com.game.agar.entities;

import com.badlogic.gdx.graphics.Color;
import com.game.agar.tools.Position;

import java.util.Random;


public abstract class Entity {

    static Random random = new Random();
    Position position;
    float radius;
    int weight;
    Color color;

    Entity(Position position, int weight){
        this.position = position;
        this.weight = weight;
        this.radius = (float)Math.sqrt(weight/Math.PI);
        float red = random.nextFloat();
        float green = random.nextFloat();
        float blue = random.nextFloat();
        this.color = new Color(red,green,blue,1);
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
        return distance < this.radius + collidingObject.getRadius();
    }
}
