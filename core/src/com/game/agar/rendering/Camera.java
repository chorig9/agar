package com.game.agar.rendering;

import com.badlogic.gdx.math.Matrix4;
import com.game.agar.tools.Position;

public class Camera {

    private Position position;
    private float zoom = 1;
    private int width, height;

    public Camera(int width, int height){
        this.width = width;
        this.height = height;

        position = new Position(width / 2.f, width / 2.f);
    }

    public void changeZoom(double change){
        zoom += change;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public Matrix4 getMatrix(){

        Matrix4 matrix = new Matrix4();
        Matrix4 translation = new Matrix4();

        matrix.setToScaling(zoom, zoom, 0);

        float x = width / 2.f;
        float y = height / 2.f;

        // centering object and translating accordingly to camera position and zoom
        translation.setToTranslation(x - zoom * x - (position.x - x) * zoom, y - zoom * y - (position.y -y )* zoom, 0);
        matrix = translation.mul(matrix);

        return matrix;
    }
}
