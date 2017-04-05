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

    public Position copy(){
        return new Position(x, y);
    }

}
