package com.game.agar;

import com.badlogic.gdx.*;
import com.game.agar.control.Controller;
import com.game.agar.entities.Ball;
import com.game.agar.entities.Player;
import com.game.agar.rendering.Camera;
import com.game.agar.entities.Entity;
import com.game.agar.rendering.IRenderer;
import com.game.agar.rendering.SceneRenderer;
import com.game.agar.tools.Position;

import java.util.List;
import java.util.ArrayList;


public class Game extends ApplicationAdapter{

	private IRenderer renderer;
	private Camera camera;
	private List<Entity> entities;

	private Player player;

	@Override
	public void create () {
		camera = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		entities = new ArrayList<>();

		// TESTING
		entities.add(new Ball(new Position(10,10)));
		entities.add(new Ball(new Position(100,10)));
		entities.add(new Ball(new Position(10,100)));
		player = new Player(new Position(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() /2 ));
		entities.add(player);

		renderer = new SceneRenderer(camera, entities);
		renderer.init();

		Gdx.input.setInputProcessor(new Controller(camera, player));
    }

	@Override
	public void render () {
		player.move(1);
		camera.setPosition(player.getPosition());

		renderer.renderFrame();
	}

	@Override
	public void dispose () {
		renderer.dispose();
	}
}
