package com.game.agar;

import com.badlogic.gdx.*;
import com.game.agar.rendering.Camera;
import com.game.agar.entities.Entity;
import com.game.agar.rendering.IRenderer;
import com.game.agar.rendering.SceneRenderer;

import java.util.List;
import java.util.ArrayList;


public class Game extends ApplicationAdapter{

	//using the example renderer for presentation purposes
	private IRenderer renderer;

	@Override
	public void create () {
		Camera camera = new Camera();
		List<Entity> entities = new ArrayList<>();

		renderer = new SceneRenderer(camera, entities);
		renderer.init();
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
