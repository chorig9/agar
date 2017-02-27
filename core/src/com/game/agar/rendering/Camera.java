package com.game.agar.rendering;


import com.game.agar.tools.Position;

public class Camera {

    private Position position;
    private double zoom;

    public Position getPosition() {
        return position;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    //TODO...
}
