package com.game.agar.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.game.agar.entities.Player;
import com.game.agar.rendering.Camera;

public class Controller extends InputAdapter{

    private Camera camera;
    private Player player;

    public Controller(Camera camera, Player player){
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
        double angle = Math.atan2(-(screenY - Gdx.graphics.getHeight() / 2),
                (screenX - Gdx.graphics.getWidth()  / 2));

        player.setMovingDirection(angle);

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.changeZoom(amount / 10.);
        return false;
    }
}
