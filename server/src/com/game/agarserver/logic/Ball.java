package com.game.agarserver.logic;

import com.game.agar.shared.Position;
import com.game.agarserver.eventsystem.events.BallMoveEvent;
import com.game.agarserver.eventsystem.events.BallRadiusChangeEvent;
import com.game.agarserver.tools.Vector;


public class Ball extends Entity{
    private static final double SPLITING_SPEED_MULTIPLIER = 3;
    private static long next_id = 0;

    private long id;
    private long ownerId;
    private double moveAngle = 0;
    private double speedMultiplier = 1;

    private Position force = new Vector(0, 0);

    public Ball(World world, Position position, int radius, long ownerId){
        super(world, position, radius);
        this.ownerId = ownerId;
        this.id = (next_id++) % Long.MAX_VALUE;
    }

    public double getSpeed(){
        return 250 / radius;
    }

    public long getId(){    return id;  }

    public long getOwnerId() {  return ownerId; }

    public double getMoveAngle(){
        return moveAngle;
    }

    public void setSpeedMultiplier(double acceleration){ this.speedMultiplier=acceleration;};
    public double getSpeedMultiplier(){return speedMultiplier;}

    public void loseAcceleration(){
        if(speedMultiplier>1)
            speedMultiplier -= 0.01;
        else
            speedMultiplier = 1;
    }

    public void setMoveAngle(double angle){
        moveAngle = angle;
    }

    public void resetForce(){
        force.x = force.y = 0;
    }

    public Position getMovementVector(){
        double x = Math.cos(moveAngle) * (getSpeed()*getSpeedMultiplier()); // + force.x;
        double y = Math.sin(moveAngle) * (getSpeed()*getSpeedMultiplier()); // + force.y;

        return new Position(x, y);
    }

    public Position getNextPosition(){
        return Vector.sum(position, getMovementVector(), force);
    }

    public void move(){
        position = getNextPosition();
        resetForce();
        loseAcceleration();
        world.getEventProcessor().issueEvent(new BallMoveEvent(this, position));
    }

    // this method is called only for balls 'responsible' for collision, that is
    // for balls touching another ball and moving towards it
    public boolean isCollidingWith(Entity otherObject){
        double distance = position.distanceTo(otherObject.getPosition());
        boolean areEntitiesCloseEnough = distance <= this.radius + otherObject.radius;

        double nextDistance = getNextPosition().distanceTo(otherObject.getPosition());
        boolean isBallAtCollisionCourse = nextDistance < distance;

        return areEntitiesCloseEnough && isBallAtCollisionCourse;
    }

    public void eatFood(Entity food){
        updateRadius(food.getWeight());
        food.die();
    }

    public void handleCollision(Ball ball){
        if (ownerId == ball.getOwnerId()) {
            Position pull = new Position(ball.position.x-position.x, ball.position.y-position.y);
            Position toBall = Vector.projection(getMovementVector(), pull);

            force.x += - toBall.x;
            force.y += - toBall.y;

        } else {
            if (radius >= ball.radius) {
                updateRadius(ball.getWeight());
                ball.die();
            } else {
                ball.updateRadius(getWeight());
                die();
            }
        }
    }

    public void updateRadius(double massGained){
        radius = Math.sqrt(radius * radius + massGained / Math.PI);
        setMoveAngle(moveAngle);
        resetForce();
        getWorld().getEventProcessor().issueEvent(new BallRadiusChangeEvent(this, radius));
    }

    public Ball splitAndGetNewBall(){
        updateRadius(-getWeight()/2);
        Ball newBall = new Ball(getWorld(), getNextPosition(),(int)getRadius(),ownerId);
        newBall.setSpeedMultiplier(SPLITING_SPEED_MULTIPLIER);
        return newBall;
    }
}
