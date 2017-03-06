package com.game.agar;

import com.badlogic.gdx.*;
import com.game.agar.communication.CommunicationManager;
import com.game.agar.communication.Handler;
import com.game.agar.control.Controller;
import com.game.agar.entities.Player;
import com.game.agar.rendering.Camera;
import com.game.agar.entities.Entity;
import com.game.agar.rendering.IRenderer;
import com.game.agar.rendering.SceneRenderer;
import com.game.agar.tools.Position;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


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

		// TESTING
		player = new Player(new Position(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2), 10);
		entities.add(player);

		renderer = new SceneRenderer(camera, entities);
		renderer.init();

		manager = new CommunicationManager(this::handleError);

		Gdx.input.setInputProcessor(new Controller(camera, manager));

		handler = new Handler(entities, player);
		manager.setCommunicationListener(handler::handleRequest);

		manager.start();
    }

    private void handleError(Exception error){
		//error.printStackTrace();
	}

	@Override
	public void render () {
		camera.setPosition(player.getPosition());
		renderer.renderFrame();
	}

	@Override
	public void dispose () {
		renderer.dispose();
		manager.end();
	}
}