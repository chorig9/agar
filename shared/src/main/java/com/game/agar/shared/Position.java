package com.game.agar.shared;

public class Position {

    public double x, y;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Position otherPosition){
        double dx = x - otherPosition.x;
        double dy = y - otherPosition.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Position))
            return false;
        else {
            Position p = (Position) o;
            return p.x == x && p.y == y;
        }
    }

    @Override
    public Position clone(){
        return new Position(x, y);
    }


    public static Position sum(Position a, Position b) {
        return new Position(a.x + b.x, a.y + b.y);
    }

    public static double distanceBetween(Position a, Position b) {
        return a.distanceTo(b);
    }

    public static Position vectorProjection(Position a, Position b) {
        double multi = a.x * b.x + a.y * b.y;
        double length = b.x * b.x + b.y * b.y;
        return new Position(b.x * multi / length, b.y * multi / length);
    }

}
