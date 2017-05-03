package com.game.agar.entities;

import com.badlogic.gdx.graphics.Color;
import com.game.agar.shared.Position;

import java.util.Random;


public abstract class Entity {

    static Random random = new Random();
    Color color;

    private final long entityId;

    Entity(long entityId){
        float red = random.nextFloat();
        float green = random.nextFloat();
        float blue = random.nextFloat();
        this.color = new Color(red,green,blue,1);
        this.entityId = entityId;
    }

    public abstract Position getPosition();
    public abstract double getRadius();
    public Color getColor() { return color; }

    public long getEntityId() {
        return entityId;
    }
}
