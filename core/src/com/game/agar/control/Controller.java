package com.game.agar.control;

import com.badlogic.gdx.InputAdapter;
import com.game.agar.communication.CommunicationManager;
import com.game.agar.entities.Player;
import com.game.agar.rendering.Camera;

public class Controller extends InputAdapter{

    private Camera camera;
    private CommunicationManager manager;
    private Player player;

    public Controller(Camera camera, CommunicationManager manager, Player player){
        this.camera = camera;
        this.manager = manager;
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
        // TODO
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        manager.updateMoveAngles(screenX,screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.changeZoom(amount / 10.);
        return false;
    }
}
