package com.game.agar;

import com.badlogic.gdx.*;
import com.game.agar.control.Controller;
import com.game.agar.entities.Ball;
import com.game.agar.rendering.Camera;
import com.game.agar.entities.Entity;
import com.game.agar.rendering.IRenderer;
import com.game.agar.rendering.SceneRenderer;
import com.game.agar.tools.Position;

import java.util.List;
import java.util.ArrayList;


public class Game extends ApplicationAdapter{

	//using the example renderer for presentation purposes
	private IRenderer renderer;

	@Override
	public void create () {
		Camera camera = new Camera();
		List<Entity> entities = new ArrayList<>();

		// TESTING
		entities.add(new Ball(new Position(10,10)));
		entities.add(new Ball(new Position(100,10)));
		entities.add(new Ball(new Position(10,100)));

		renderer = new SceneRenderer(camera, entities);
		renderer.init();

		Gdx.input.setInputProcessor(new Controller(camera, entities));
    }

	@Override
	public void render () {
		renderer.renderFrame();
	}

	@Override
	public void dispose () {
		renderer.dispose();
	}
}
