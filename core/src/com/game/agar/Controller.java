package com.game.agar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.game.agar.tools.Position;

/**
 * Created by Mily on 2017-02-28.
 */
public class Controller extends InputAdapter {
    private Position mousePosition;

    Controller(){
        mousePosition = new Position(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePosition.x = screenX;
        mousePosition.y = Gdx.graphics.getHeight() - screenY;
        return false;
    }

    public Position getMousePosition(){
        return mousePosition;
    }
}
