package com.game.agar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.game.agar.communication.Handler;
import com.game.agar.control.Controller;
import com.game.agar.entities.Ball;
import com.game.agar.entities.Entity;
import com.game.agar.entities.Player;
import com.game.agar.rendering.Camera;
import com.game.agar.rendering.SceneRenderer;
import com.game.agar.shared.Connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class Game extends ApplicationAdapter{

	private SceneRenderer renderer;
	private Camera camera;
	private Handler handler;
	private List<Entity> entities;
	private Connection connection;
	private Player player;

	@Override
	public void create () {
		camera = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		entities = Collections.synchronizedList(new ArrayList<>());
		player = new Player();
		Map<Long, Ball> playerBalls = player.getBalls();

		renderer = new SceneRenderer(camera, entities);
		renderer.init();

		connection = Connection.createConnectionTo("localhost", 1234);
		Gdx.input.setInputProcessor(new Controller(camera, connection, player));

		handler = new Handler(entities, playerBalls);
		connection.setCommunicationListener(handler::handleRequest);

		connection.start();

		handler.waitForFirstBall();
	}

	@Override
	public void render () {
		camera.setPosition(player.getBiggestBall().getPosition());
		renderer.renderFrame();
		System.out.println(entities.size());
	}

	@Override
	public void dispose () {
		renderer.dispose();
		connection.end();
	}
}