package com.game.agar.entities;

import com.badlogic.gdx.graphics.Color;
import com.game.agar.tools.Position;
import com.game.agar.tools.PositionConverger;

public class Player extends Entity {

    private double movingDirectionAngle;
    private PositionConverger drawingPosition;

    public Player(Position position, int weight) {
        super(position, weight);

        drawingPosition = new PositionConverger(position.copy(), position);
        color = Color.RED;
    }

    @Override
    public Position getDrawingPosition() {
        return drawingPosition.getValue();
    }

    public void setMovingDirection(double angle) {
        this.movingDirectionAngle = angle;
        drawingPosition.convergeNow();
    }

    public void move(double translation) {
        realPosition.x += translation * Math.cos(movingDirectionAngle);
        realPosition.y += translation * Math.sin(movingDirectionAngle);

        drawingPosition.convergeNow();
        //drawingPosition.doConvergeFully(200);
    }

    public void eat (Entity collidingObject){
        int massGained = collidingObject.getWeight();

        updateRadius(massGained);
    }

}
