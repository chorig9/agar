package com.game.agar.tools;

public class Position {

    public float x, y;

    public Position(float x, float y){
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

    @Override
    public String toString() {
        return Float.toString(x) + ":" + Float.toString(y);
    }

    public Position copy(){
        return new Position(x, y);
    }



}
