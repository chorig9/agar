package com.game.agar.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.game.agar.communication.CommunicationManager;
import com.game.agar.entities.Ball;
import com.game.agar.entities.Player;
import com.game.agar.rendering.Camera;
import com.game.agar.tools.Position;
import org.json.JSONObject;

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
        Position basePosition = player.getBiggestBall().getPosition();
        for (Ball ball: player.getBalls()) {
            Position thisBallPosition = ball.getPosition();
            Position difference = new Position(thisBallPosition.x-basePosition.x,thisBallPosition.y-basePosition.y);
            Position screenRelativePosition =
                    new Position(Gdx.graphics.getWidth()  / 2 + difference.x,Gdx.graphics.getHeight() / 2 - difference.y);
            double angle = Math.atan2(-(screenY-screenRelativePosition.y),screenX-screenRelativePosition.x);
            ball.setMoveAngle(angle);

            JSONObject json = new JSONObject();
            json.put("action", "mouse_move");
            json.put("id",ball.getId());
            json.put("angle", angle);
            manager.send(json.toString());
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.changeZoom(amount / 10.);
        return false;
    }
}
