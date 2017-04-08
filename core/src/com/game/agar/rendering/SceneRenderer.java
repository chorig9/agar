package com.game.agar.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.game.agar.entities.Ball;
import com.game.agar.entities.Entity;
import com.game.agar.shared.Position;

import java.util.HashMap;
import java.util.List;

public class SceneRenderer{

    private final List<Entity> entities;
    private Camera camera;
    private ShapeRenderer shapeRenderer;
    private HashMap<Long, Position> vecs;
    private HashMap<Long, Position> forc;
    public SceneRenderer(Camera camera, List<Entity> entities, HashMap<Long, Position> vecs, HashMap<Long, Position> forc){
        this.camera = camera;
        this.entities = entities;
        this.vecs = vecs;
        this.forc = forc;
    }

    public void init() {
        shapeRenderer = new ShapeRenderer();
    }

    public void renderFrame() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.setTransformMatrix(camera.getMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        synchronized (entities) {
            entities.forEach(this::renderEntity);
        }
        shapeRenderer.end();
    }

    private void renderEntity(Entity entity){
        shapeRenderer.setColor(entity.getColor());
        shapeRenderer.circle((float)entity.getPosition().x,(float) entity.getPosition().y,(float) entity.getRadius());

        if(entity instanceof Ball) {

            if(!vecs.containsKey(((Ball) entity).getId()))
                return;

            Position v = vecs.get(((Ball) entity).getId());
            Position f = forc.get(((Ball) entity).getId());

            shapeRenderer.setColor(Color.BLACK);
            float x = (float) entity.getPosition().x;
            float y = (float) entity.getPosition().y;
            shapeRenderer.line(x, y, (float) (x + v.x * 50), (float) (y + v.y * 50));

            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.line(x, y, (float) (x + f.x * 50), (float) (y + f.y * 50));

            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.line(x, y, (float) (x + (f.x + v.x) * 50), (float) (y + (f.y + v.y) * 50));
        }
    }

    public void dispose(){
        shapeRenderer.dispose();
    }

}
