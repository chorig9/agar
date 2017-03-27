package com.game.agar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.game.agar.communication.CommunicationManager;
import com.game.agar.communication.Handler;
import com.game.agar.control.Controller;
import com.game.agar.entities.Ball;
import com.game.agar.entities.Entity;
import com.game.agar.entities.Player;
import com.game.agar.rendering.Camera;
import com.game.agar.rendering.IRenderer;
import com.game.agar.rendering.SceneRenderer;
import com.game.agar.tools.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class Game extends ApplicationAdapter{

	private IRenderer renderer;
	private Camera camera;
    private Handler handler;
	private List<Entity> entities;
	private CommunicationManager manager;
	private Player player;

	@Override
	public void create () {
		camera = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		entities = Collections.synchronizedList(new ArrayList<>());
		player = new Player();
		Map<Long, Ball> playerBalls = player.getBalls();

		// TESTING
		Ball initialBall = new Ball(new Position(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), 10,0);
		playerBalls.put(initialBall.getId(), initialBall);
		entities.add(initialBall);
		initialBall = new Ball(new Position(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 100), 10,1);
		playerBalls.put(initialBall.getId(), initialBall);
		entities.add(initialBall);

		renderer = new SceneRenderer(camera, entities);
		renderer.init();

		manager = new CommunicationManager(player,this::handleError);

		Gdx.input.setInputProcessor(new Controller(camera, manager, player));

		handler = new Handler(entities, playerBalls, manager);
		manager.setCommunicationListener(handler::handleRequest);

		manager.start();
    }

    private void handleError(Exception error){
		//error.printStackTrace();
	}

	@Override
	public void render () {
		camera.setPosition(player.getBiggestBall().getPosition());
		renderer.renderFrame();
	}

	@Override
	public void dispose () {
		renderer.dispose();
		manager.end();
	}
}