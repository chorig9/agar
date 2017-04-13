package com.game.agarserver.tools;

import com.game.agar.shared.Position;

public class Vector extends Position{

    public Vector(double x, double y) {
        super(x, y);
    }

    public double length(){
        return Math.sqrt(x * x + y * y);
    }

    public double distanceTo(Position otherPosition){
        double dx = x - otherPosition.x;
        double dy = y - otherPosition.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public Vector sum(Position p) {
        Vector result = new Vector(0, 0);

        result.x = x + p.x;
        result.y = y + p.y;

        return result;
    }

    public static Vector projection(Position a, Position b) {
        double multi = a.x * b.x + a.y * b.y;
        double length = (b.x * b.x + b.y * b.y);
        return new Vector(b.x * multi / length, b.y * multi / length);
    }

    public double dotProduct(Position p){
        return x * p.x + y * p.y;
    }
}

