package com.game.agar;

import com.badlogic.gdx.*;
import com.game.agar.communication.Handler;
import com.game.agar.control.Controller;
import com.game.agar.entities.Ball;
import com.game.agar.entities.Player;
import com.game.agar.rendering.Camera;
import com.game.agar.entities.Entity;
import com.game.agar.rendering.IRenderer;
import com.game.agar.rendering.SceneRenderer;
import com.game.agar.tools.Position;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;


public class Game extends ApplicationAdapter{

	private IRenderer renderer;
	private Camera camera;
    private Handler handler;
	private List<Entity> entities;

	private Player player;

	@Override
	public void create () {
		camera = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		entities = new ArrayList<>();

		// TESTING
		entities.add(new Ball(new Position(10,10),100));
		entities.add(new Ball(new Position(100,10),200));
		entities.add(new Ball(new Position(10,100),300));
		player = new Player(new Position(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() /2 ),350);
		entities.add(player);

		renderer = new SceneRenderer(camera, entities);
		renderer.init();

		handler = new Handler(entities, this::handleError);

		Gdx.input.setInputProcessor(new Controller(camera, player));

// Position converging testing
//		Timer timer = new Timer();
//		TimerTask growth = new TimerTask() {
//			@Override
//			public void run() {
//				player.move(10);
//			}
//		};
//		timer.schedule(growth, 0, 250);
    }

    private void handleError(Exception error){
		error.printStackTrace();
	}

	@Override
	public void render () {
		// move player and camera
		player.move(1);

		update();
		camera.setPosition(player.getDrawingPosition());
		renderer.renderFrame();
	}

	@Override
	public void dispose () {
		renderer.dispose();
	}

	private void update() {
		Predicate<Entity> isEntityColliding = entity -> !entity.equals(player) && entity.isCollision(player);

		entities.stream().filter(isEntityColliding).forEach(entity -> player.eat(entity));
		entities.removeIf(isEntityColliding);
	}
}