package com.game.agar;

import com.badlogic.gdx.*;
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
	private Controller controller;
	List<Entity> entities;

	@Override
	public void create () {
		Camera camera = new Camera();
		entities = new ArrayList<>();
		controller = new Controller();
		Gdx.input.setInputProcessor(controller);
		Ball ball = new Ball(new Position(300,300));
		entities.add(ball);
		renderer = new SceneRenderer(camera, entities);
		renderer.init();
    }

	@Override
	public void render () {
		update();
		renderer.renderFrame();
	}

	@Override
	public void dispose () {
		renderer.dispose();
	}
	
	public void update (){
		Position targetPosition = controller.getMousePosition();
		for (Entity entity: entities) {
			Position currentEntityPosition = entity.getPosition();
			double theta = Math.atan2(targetPosition.y-currentEntityPosition.y,targetPosition.x-currentEntityPosition.x);
			double dx = 0.5*Math.cos(theta);
			double dy = 0.5*Math.sin(theta);
			if(!currentEntityPosition.equals(targetPosition)){
				currentEntityPosition.x += dx;
				currentEntityPosition.y += dy;
			}
			entity.getSprite();
		}
	}
}
