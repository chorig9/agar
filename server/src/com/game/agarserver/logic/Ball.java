package com.game.agarserver.logic;

import com.game.agarserver.tools.Vector;

public class Ball extends Entity{
    private static final double SPLITING_SPEED_MULTIPLIER = 3;
    private static long next_id = 0;

    private long id;
    private long ownerId;
    private double moveAngle = 0;
    private double speedMultiplier = 1;

    private Vector movementVector = calculateMovementVector();

    public Ball(Vector position, int radius, long ownerId){
        super(position, radius);
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

    public void setSpeedMultiplier(double acceleration){ this.speedMultiplier=acceleration;}

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
  
    public Vector calculateMovementVector(){
        double x = Math.cos(moveAngle) * (getSpeed()*getSpeedMultiplier());
        double y = Math.sin(moveAngle) * (getSpeed()*getSpeedMultiplier());

        return new Vector(x, y);
    }

    public Vector getNextPosition(){
        return movementVector.sum(position);
    }

    public void move(){
        position = getNextPosition();
        loseAcceleration();
        listener.accept(PacketFactory.createPositionPacket(id, position));

        movementVector = calculateMovementVector();
    }

    // this returns true only for balls 'responsible' for collision, that is
    // for balls touching another ball and moving towards it
    public boolean isCollidingWith(Ball otherObject) {
        if(this == otherObject)
            return false;

        double distance = position.distanceTo(otherObject.getPosition());
        boolean areEntitiesCloseEnough = distance <= this.radius + otherObject.radius;

        double nextDistance = getNextPosition().distanceTo(otherObject.getPosition());
        boolean isBallAtCollisionCourse = nextDistance < distance;

        boolean test = true;
        if(otherObject.speedMultiplier != 1 || speedMultiplier != 1)
            test = false;

        return areEntitiesCloseEnough && isBallAtCollisionCourse && test;
    }

    public void checkAndHandleEating(Entity food){
        if(canEat(food))
            eatFood(food);
    }

    public void checkAndHandleCollision(Ball ball){
        if(isCollidingWith(ball)){
            if (ownerId == ball.getOwnerId()) {
                handleSamePlayerCollision(ball);
            } else {
                handleDifferentPlayerCollision(ball);
            }
        }
    }

    public boolean canEat(Entity food){
        return position.distanceTo(food.getPosition()) < this.radius + food.radius;
    }

    private void eatFood(Entity food){
        System.out.print("!");
        updateRadius(food.getWeight());
        food.die();
    }

    private void handleDifferentPlayerCollision(Ball ball){
        if (radius >= ball.radius) {
            updateRadius(ball.getWeight());
            ball.die();
        } else {
            ball.updateRadius(getWeight());
            die();
        }
    }

    private void handleSamePlayerCollision(Ball ball){
        Vector pull = new Vector(ball.position.x-position.x, ball.position.y-position.y);

        Vector toBall = Vector.projection(movementVector, pull);
        Vector rest = new Vector(movementVector.x - toBall.x, movementVector.y - toBall.y);

        Vector fromBall = Vector.projection(ball.movementVector, pull);
        if(ball.isCollidingWith(this)){
            fromBall.x = fromBall.y = 0;
        }

        movementVector.x = rest.x;
        movementVector.y = rest.y;

        if(getSpeed() > ball.getSpeed()) {
            movementVector.x += fromBall.x;
            movementVector.y += fromBall.y;
        }
    }

    private void updateRadius(double massGained){
        radius = Math.sqrt(radius * radius + massGained / Math.PI);
        setMoveAngle(moveAngle);
        movementVector = calculateMovementVector();
        listener.accept(PacketFactory.createRadiusPacket(id, radius));
    }

    public Ball splitAndGetNewBall(){
        updateRadius(-getWeight()/2);
        Ball newBall = new Ball(getNextPosition(),(int)getRadius(),ownerId);
        newBall.setSpeedMultiplier(SPLITING_SPEED_MULTIPLIER);
        return newBall;
    }
}
