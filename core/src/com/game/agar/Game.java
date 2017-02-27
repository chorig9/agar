package com.game.agar;

import com.badlogic.gdx.*;
import com.game.agar.rendering.ExampleRenderer;
import com.game.agar.rendering.IRenderer;


public class Game extends ApplicationAdapter{

	//using the example renderer for presentation purposes
	private IRenderer renderer = new ExampleRenderer();

	@Override
	public void create () {
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
