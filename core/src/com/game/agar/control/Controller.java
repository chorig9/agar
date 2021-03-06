package com.game.agar.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.game.agar.entities.Player;
import com.game.agar.rendering.Camera;
import com.game.agar.shared.Connection;
import org.json.JSONObject;

import java.io.IOException;

public class Controller extends InputAdapter{

    private Camera camera;
    private Connection connection;
    private Player player;

    public Controller(Camera camera, Connection connection, Player player){
        this.camera = camera;
        this.connection = connection;
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        JSONObject json = new JSONObject();
        json.put("action", "split_attempt");
        try {
            connection.send(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
            // TODO
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        screenX -= Gdx.graphics.getWidth() / 2;

        screenY = Gdx.graphics.getHeight() - screenY;
        screenY -= Gdx.graphics.getHeight() / 2;

        JSONObject json = new JSONObject();
        json.put("action", "mouse_update");
        json.put("x", (int) (screenX / camera.getZoom()));
        json.put("y", (int) (screenY / camera.getZoom()));
        try {
            connection.send(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
            // TODO
        }

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.changeZoom(amount / 10.);
        return false;
    }
}
