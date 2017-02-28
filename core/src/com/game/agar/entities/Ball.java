package com.game.agar.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.agar.tools.Position;

public class Ball extends Entity {

    public Ball(Position position) {

        super(position, null);

        Pixmap pixmap = new Pixmap(16,16, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fillCircle(8,8,8);
        Texture texture = new Texture(pixmap);

        sprite = new Sprite(texture);
        offset = 8;
    }
}
