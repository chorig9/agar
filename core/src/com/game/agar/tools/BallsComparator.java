package com.game.agar.tools;

import com.game.agar.entities.Ball;
import java.lang.Float;
import java.util.Comparator;

public class BallsComparator implements Comparator<Ball> {
    @Override
    public int compare(Ball o1, Ball o2) {
        Float o1Rad = new Float(o1.getRadius());
        Float o2Rad = new Float(o2.getRadius());
        return o1Rad.compareTo(o2Rad);
    }
}
