package com.game.agar.entities;

import com.badlogic.gdx.graphics.Color;
import com.game.agar.tools.FloatConverger;
import com.game.agar.tools.Position;
import java.util.Random;


public abstract class Entity {

    static Random random = new Random();
    Color color;

    Entity(){
        float red = random.nextFloat();
        float green = random.nextFloat();
        float blue = random.nextFloat();
        this.color = new Color(red,green,blue,1);
    }

    // in basic case realPosition == drawingPosition
    public abstract Position getPosition();
    public abstract float getRadius();
    public Color getColor() { return color; }

}
