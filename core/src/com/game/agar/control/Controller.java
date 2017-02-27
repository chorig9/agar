package com.game.agar.control;

import com.badlogic.gdx.InputAdapter;
import com.game.agar.rendering.Camera;

public class Controller extends InputAdapter{

    private Camera camera;
    private Object player;

    public Controller(Camera camera, Object player){
        this.camera = camera;
        this.player = player;
    }


    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
        // TODO
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
        // TODO
    }

    @Override
    public boolean scrolled(int amount) {
        camera.changeZoom(amount / 10.);
        return false;
    }
}
