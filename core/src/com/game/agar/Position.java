package com.game.agar;

public class Position {

    public double x, y;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
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
}
