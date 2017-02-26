package com.game.agar;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;


public class Game extends ApplicationAdapter{
	private SpriteBatch batch;
    private Texture playerTexture, ballTexture;
    private Sprite sprite, ball;
    private FrameContainer frame;

    private double angle = 0;
    private int width, height;

	@Override
	public void create () {
		batch = new SpriteBatch();

        playerTexture = new Texture("badlogic.jpg");
        sprite = new Sprite(playerTexture);

        ballTexture = new Texture("object.png");
        ball = new Sprite(ballTexture);

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        frame = new FrameContainer(width, height);

        startAddingNewObjectsTask();
        startTranslationTask();
        setInputProcessor();
    }

    private void setInputProcessor(){
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                angle = Math.atan2(screenY - Gdx.graphics.getHeight() / 2,
                        screenX - Gdx.graphics.getWidth() / 2);

                return false;
            }
        });
    }

    private void startTranslationTask(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                frame.translate(-Math.cos(angle), Math.sin(angle));
                frame.getObjects().removeIf(object -> isObjectInsidePlayer(object));
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 10);
    }

    private boolean isObjectInsidePlayer(Position object){
        return object.x > width / 2 - sprite.getWidth() / 2 && object.x < width / 2 + sprite.getWidth() / 2
                && object.y > height / 2 - sprite.getHeight() / 2 && object.y < height / 2 + sprite.getHeight() / 2;
    }

    private void startAddingNewObjectsTask(){
        TimerTask task = new TimerTask() {
            Random generator = new Random();
            @Override
            public void run() {
                frame.addObjectAt(new Position(generator.nextInt(width), generator.nextInt(height)));
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
        {
            sprite.setPosition(width / 2 - sprite.getWidth() / 2, height / 2 - sprite.getHeight() / 2);
            sprite.draw(batch);

            frame.getObjects().forEach(object -> {
                ball.setPosition((int) object.x, (int) object.y);
                ball.draw(batch);
            });
        }
        batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
        ballTexture.dispose();
        playerTexture.dispose();
	}
}
