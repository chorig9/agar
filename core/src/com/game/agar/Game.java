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
import com.game.agar.tools.ITask;
import com.game.agar.tools.Position;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Predicate;


public class Game extends ApplicationAdapter{

	private IRenderer renderer;
	private Camera camera;
    private Handler handler;
	private List<Entity> entities;

	private Player player;
	private final Queue<ITask> tasks = new ArrayBlockingQueue<>(10);

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

		handler = new Handler(entities, this, this::handleError);

		Gdx.input.setInputProcessor(new Controller(camera, player));
    }

    private void handleError(Exception error){
		// TODO
		error.printStackTrace();
	}

	public void addTask(ITask task){
		tasks.add(task);
	}

	@Override
	public void render () {
		// run all queued tasks
		synchronized(tasks) {
			tasks.forEach(ITask::run);
			tasks.clear();
		}

		// move player and camera
		player.move(1);

		update();
		camera.setPosition(player.getPosition());

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