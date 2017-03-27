package com.game.agar.entities;

import com.game.agar.shared.Position;
import com.game.agar.tools.BallsComparator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Player {
    private Map<Long, Ball> balls;
    private int score;

    public Player(){
        balls = new HashMap<>();
        score = 0;
    }
    public Map<Long, Ball> getBalls() {return balls;}
    public int getScore() {return score;}
    public Ball getBiggestBall() {
        return Collections.max(balls.values(), new BallsComparator());
}

    public Position getMassCenter() {
        float totalMass = 0, totalX = 0, totalY = 0;
        for(Ball ball : balls.values()){
            totalMass += ball.getWeight();
            totalX += ball.getWeight() * ball.getPosition().x;
            totalY += ball.getWeight() * ball.getPosition().y;
        }
        return new Position(totalX/totalMass,totalY/totalMass);
    }
}
