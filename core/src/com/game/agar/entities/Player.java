package com.game.agar.entities;

import com.game.agar.tools.BallsComparator;
import com.game.agar.tools.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mily on 2017-03-07.
 */
public class Player {
    private List<Ball> balls;
    private int score;

    public Player(){
        balls = new ArrayList<>();
        score = 0;
    }
    public List<Ball> getBalls() {return balls;}
    public int getScore() {return score;}
    public Ball getBiggestBall() {
        return Collections.max(balls,new BallsComparator());
}

    public Position getMassCenter() {
        float totalMass = 0, totalX = 0, totalY = 0;
        for(Ball ball : balls){
            totalMass += ball.getWeight();
            totalX += ball.getWeight() * ball.getPosition().x;
            totalY += ball.getWeight() * ball.getPosition().y;
        }
        return new Position(totalX/totalMass,totalY/totalMass);
    }
}
