package com.game.agar.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class SceneRenderer implements IRenderer{

    private List<Entity> entities = new ArrayList<>();
    private Camera camera;
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
