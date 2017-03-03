package com.game.agar.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.game.agar.entities.Entity;
import com.game.agar.entities.Player;

import java.util.List;

public class SceneRenderer implements IRenderer{

    private List<Entity> entities;
    private Camera camera;
    private ShapeRenderer shapeRenderer;

    public SceneRenderer(Camera camera, List<Entity> entities){
        this.camera = camera;
        this.entities = entities;
    }

    @Override
    public void init() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void renderFrame() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setTransformMatrix(camera.getMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        {
            entities.forEach(this::renderEntity);
        }
        shapeRenderer.end();
    }

    private void renderEntity(Entity entity){
        shapeRenderer.setColor(entity.getColor());
        shapeRenderer.circle(entity.getPosition().x,entity.getPosition().y,entity.getRadius());
    }

    @Override
    public void dispose(){
        shapeRenderer.dispose();
    }

}
