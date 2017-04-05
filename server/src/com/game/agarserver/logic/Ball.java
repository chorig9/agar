package com.game.agarserver.logic;

import com.game.agar.shared.Position;

import static com.game.agar.shared.Position.*;

public class Ball extends Entity{

    private static long next_id = 0;

    private long id;
    private long ownerId;
    private double moveAngle;
    private double speed;

    private Position force = new Position(0, 0);

    public Ball(Position position, int radius, long ownerId){
        super(position, radius);
        this.ownerId = ownerId;
        this.id = (next_id++) % Long.MAX_VALUE;
        this.speed = 50/radius;
    }

    public long getId(){    return id;  }

    public long getOwnerId() {  return ownerId; }

    public double getMoveAngle(){
        return moveAngle;
    }

    public void setMoveAngle(double angle){
        moveAngle = angle;
    }

    public void resetForce(){
        force.x = force.y = 0;
    }

    public Position getMovementVector(){
        double x = Math.cos(moveAngle) * speed + force.x;
        double y = Math.sin(moveAngle) * speed + force.y;

        return new Position(x, y);
    }

    public Position getNextPosition(){
        return sum(position, getMovementVector());
    }

    public void move(){
        position = getNextPosition();
        resetForce();

        listener.accept(PacketFactory.createPositionPacket(id, position));
    }

    public boolean isCollidingWith(Entity otherObject){
        double distance = getNextPosition().distanceTo(otherObject.getPosition());
        return distance < this.radius + otherObject.radius;
    }

    public void handleCollision(Entity entity){
        if(entity instanceof Ball) {
            Ball ball = (Ball) entity;
            if(speed >= ball.speed) {
                if (ownerId == ball.getOwnerId()) {
                    // if ball is getting away
                    if(distanceBetween(getNextPosition(), ball.position) > distanceBetween(position, ball.position))
                        return;
                    
                    //Position outsideForce = new Position(- position.y + ball.position.y, position.x - ball.position.x);
                    //Position p = vectorProjection(moveVector, outsideForce);
                    //moveVector.x += ball.moveVector.x;
                    //moveVector.y += ball.moveVector.y;

                    // od wektora prekości kulki odejmuję prędkość w kierunku kolizyjnej kulki (rzut na prostą)
                    Position pull = new Position(ball.position.x-position.x, ball.position.y-position.y);
                    Position toBall = vectorProjection(getMovementVector(), pull);
                    Position fromBall = vectorProjection(ball.getMovementVector(), pull);
                    force.x = fromBall.x - toBall.x;
                    force.y = fromBall.y - toBall.y;

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
            else
                ball.handleCollision(this);
        }
        else {
            updateRadius(entity.getWeight());
            entity.die();
        }
    }

    public void updateRadius(double massGained){
        radius = Math.sqrt(radius * radius + massGained / Math.PI);
        speed = 50/radius;
        setMoveAngle(moveAngle);
        resetForce();
        listener.accept(PacketFactory.createRadiusPacket(id, radius));
    }
}
