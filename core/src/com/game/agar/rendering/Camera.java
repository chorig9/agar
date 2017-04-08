package com.game.agar.rendering;

import com.badlogic.gdx.math.Matrix4;
import com.game.agar.shared.Position;

public class Camera {

    private Position position;
    private float zoom = 0.1f;
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

    public float getZoom(){
        return zoom;
    }

    public Matrix4 getMatrix(){

        Matrix4 matrix = new Matrix4();
        Matrix4 translation = new Matrix4();

        matrix.setToScaling(zoom, zoom, 0);

        float x = width / 2.f;
        float y = height / 2.f;

        // scaling around (width / 2, height / 2) point
        float centeringX = x - zoom * x;
        float centeringY = y - zoom * y;

        // translation accordingly to camera position and zoom
        float translationX = -((float)position.x - x) * zoom;
        float translationY = -((float)position.y - y) * zoom;

        translation.setToTranslation(centeringX + translationX, centeringY + translationY, 0);
        matrix = translation.mul(matrix);

        return matrix;
    }
}
