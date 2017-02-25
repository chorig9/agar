package com.game.agar;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter{
	SpriteBatch batch;
	Texture img;
    Sprite sprite;
    float posX, posY;
    double angle = 0;
    float mouseX, mouseY;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        sprite = new Sprite(img);
        posX = Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2;
        posY = Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2;

        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                mouseX = screenX - sprite.getWidth()/2;
                mouseY = Gdx.graphics.getHeight() - screenY - sprite.getHeight()/2;

                angle = Math.atan2(mouseY - posY, mouseX - posX);

                return false;
            }
        });
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Math.abs(mouseX - posX) > 5 || Math.abs(mouseY - posY) > 5){
            posX += Math.cos(angle);
            posY += Math.sin(angle);
        }

        sprite.setPosition(posX, posY);

		batch.begin();
		sprite.draw(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
