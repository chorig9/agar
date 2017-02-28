package com.game.agar.rendering;

import com.badlogic.gdx.math.Matrix4;
import com.game.agar.tools.Position;

public class Camera {

    private Position position = new Position(0, 0);
    private float zoom = 1;
    private Matrix4 matrix = new Matrix4();

    public void setPosition(Position position){
        this.position = position;
    }

    public void changeZoom(double change){
        zoom += change;
    }

    public Matrix4 getMatrix(){
        matrix.setToScaling(zoom, zoom, 0);
        matrix.setTranslation(position.x, position.y, 0);

        return matrix;
    }

}
