package com.game.agar.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.agar.entities.Entity;

import java.util.List;

public class SceneRenderer implements IRenderer{

    private List<Entity> entities;
    private Camera camera;
    private SpriteBatch batch;

    public SceneRenderer(Camera camera, List<Entity> entities){
        this.camera = camera;
        this.entities = entities;
    }

    @Override
    public void init() {
        batch = new SpriteBatch();
    }

    @Override
    public void renderFrame() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setTransformMatrix(camera.getMatrix());
        batch.begin();
        {
            entities.forEach(this::renderEntity);
        }
        batch.end();
    }

    private void renderEntity(Entity entity){
        entity.getSprite().draw(batch);
    }

    @Override
    public void dispose(){
        batch.dispose();
    }

}
