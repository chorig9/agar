package com.game.agarserver.tools;

import com.game.agar.shared.Position;

public class Vector extends Position{

    public Vector(double x, double y) {
        super(x, y);
    }

    public double getLength(){
        return Math.sqrt(x * x + y * y);
    }

    public static Position sum(Position ...vectors) {
        Vector result = new Vector(0, 0);
        for (Position vector : vectors) {
            result.x += vector.x;
            result.y += vector.y;
        }

        return result;
    }

    public static double distanceBetween(Position a, Position b) {
        return a.distanceTo(b);
    }

    public static Vector projection(Position a, Position b) {
        double multi = a.x * b.x + a.y * b.y;
        double length = (b.x * b.x + b.y * b.y);
        return new Vector(b.x * multi / length, b.y * multi / length);
    }

}
