package com.game.agar.tools;

import com.game.agar.entities.Ball;

import java.util.Comparator;

public class BallsComparator implements Comparator<Ball> {
    @Override
    public int compare(Ball o1, Ball o2) {
        Double o1Rad = o1.getRadius();
        Double o2Rad = o2.getRadius();
        return o1Rad.compareTo(o2Rad);
    }
}
