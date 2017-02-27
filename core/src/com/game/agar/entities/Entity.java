package com.game.agar.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.agar.tools.Position;

public abstract class Entity {

    Position position;
    Sprite sprite;

    Entity(Position position, Sprite sprite){
        this.position = position;
        this.sprite = sprite;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Sprite getSprite() {
        sprite.setPosition(position.x, position.y);
        return sprite;
    }
}
