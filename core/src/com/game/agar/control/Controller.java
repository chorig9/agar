package com.game.agar.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.game.agar.communication.CommunicationManager;
import com.game.agar.entities.Player;
import com.game.agar.rendering.Camera;
import org.json.JSONObject;

import java.io.IOException;

public class Controller extends InputAdapter{

    private Camera camera;
    private CommunicationManager manager;

    public Controller(Camera camera, CommunicationManager manager){
        this.camera = camera;
        this.manager = manager;
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

        JSONObject json = new JSONObject();
        json.put("action", "mouse_move");
        json.put("angle", angle);
        manager.send(json.toString());

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.changeZoom(amount / 10.);
        return false;
    }
}
