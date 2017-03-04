package com.game.agar.entities;

import com.badlogic.gdx.graphics.Color;
import com.game.agar.tools.FloatConverger;
import com.game.agar.tools.Position;
import java.util.Random;


public abstract class Entity {

    static Random random = new Random();

    Position realPosition;
    int weight;
    Color color;

    float radius;
    FloatConverger drawingRadius;

    Entity(Position position, int weight){
        this.realPosition = position;
        this.weight = weight;
        this.radius = (float)Math.sqrt(weight/Math.PI);

        drawingRadius = new FloatConverger(radius, radius);

        float red = random.nextFloat();
        float green = random.nextFloat();
        float blue = random.nextFloat();
        this.color = new Color(red,green,blue,1);
    }

    // in basic case realPosition == drawingPosition
    public Position getDrawingPosition(){
        return realPosition;
    }

    public float getRadius() { return drawingRadius.getValue(); }
    public int getWeight() { return weight; }
    public Color getColor() { return color; }

    void updateRadius(int massGained){
        weight += massGained;
        radius = (float)Math.sqrt(weight/Math.PI);
        drawingRadius.setConvergenceTo(radius);

        drawingRadius.doConvergeFully(300);
    }

    public boolean isCollision(Entity collidingObject){
        double dx = this.realPosition.x - collidingObject.realPosition.x;
        double dy = this.realPosition.y - collidingObject.realPosition.y;
        double distance = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
        return distance < this.radius + collidingObject.getRadius();
    }

}
