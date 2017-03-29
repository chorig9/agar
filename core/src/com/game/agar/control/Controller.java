package com.game.agar.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.game.agar.entities.Ball;
import com.game.agar.entities.Player;
import com.game.agar.rendering.Camera;
import com.game.agar.shared.Connection;
import com.game.agar.shared.Position;
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
        return super.keyDown(keycode);
        // TODO
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        Position basePosition = player.getBiggestBall().getPosition();
        for (Ball ball: player.getBalls().values()) {
            Position thisBallPosition = ball.getPosition();
            Position difference = new Position(thisBallPosition.x-basePosition.x,thisBallPosition.y-basePosition.y);
            Position screenRelativePosition =
                    new Position(Gdx.graphics.getWidth()  / 2 + difference.x,Gdx.graphics.getHeight() / 2 - difference.y);
            double angle = Math.atan2(-(screenY-screenRelativePosition.y),screenX-screenRelativePosition.x);
            ball.setMoveAngle(angle);

            JSONObject json = new JSONObject();
            json.put("action", "move_angle_update");
            json.put("id",ball.getId());
            json.put("angle", angle);
            try {
                connection.send(json.toString());
            } catch (IOException e) {
                e.printStackTrace();
                // TODO
            }
        }

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.changeZoom(amount / 10.);
        return false;
    }
}
