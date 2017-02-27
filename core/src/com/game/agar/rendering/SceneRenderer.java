package com.game.agar.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class SceneRenderer implements IRenderer{

    private List<Entity> entities = new ArrayList<>();
    private Camera camera = new Camera();
    private SpriteBatch batch;

    @Override
    public void init() {
        batch = new SpriteBatch();
    }

    public void addEntity(Entity entity){
        entities.add(entity);
    }

    @Override
    public void renderFrame() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setTransformMatrix(camera.getMatrix());
        batch.begin();
        {
            entities.forEach(entity -> renderModel(entity.getModel()));
        }
        batch.end();
    }

    private void renderModel(Model model){
        //TODO...
    }

    @Override
    public void dispose(){
        batch.dispose();
    }

}
