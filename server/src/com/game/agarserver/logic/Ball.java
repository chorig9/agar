package com.game.agarserver.logic;

public class Ball extends Entity{

    private static long next_id = 0;

    private long id;
    private long ownerId;
    private float moveAngle;
    private float speed;
    private boolean movable;

    public Ball(Position position, int radius, long ownerId){
        super(position, radius);
        this.ownerId = ownerId;
        this.id = (next_id++) % Long.MAX_VALUE;
        this.speed = 50/radius;
        movable=true;
    }

    public long getId(){    return id;  }

    public long getOwnerId() {  return ownerId; }

    public boolean isMoving() {return movable;}

    public void startMoving() {movable = true;}

    public void stopMoving() {movable = false;}

    public void setMoveAngle(float angle){
        moveAngle = angle;
    }

    public void moveTo(Position newPosition){
        position = newPosition;
        listener.accept(PacketFactory.createPositionPacket(id, position));
    }

    public Position getEnteredPosition() {
        float enteredX = position.x + (float)Math.cos(moveAngle) * speed;
        float enteredY = position.y + (float)Math.sin(moveAngle) * speed;
        return new Position(enteredX,enteredY);
    }

    public boolean isCollision(Position enteredPosition,Entity collidingObject){
        double dx = enteredPosition.x - collidingObject.position.x;
        double dy = enteredPosition.y - collidingObject.position.y;
        double distance = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
        return distance < this.radius + collidingObject.radius;
    }

    public void handleCollision(Entity entity){
        if(entity instanceof Ball) {
            Ball ball = (Ball) entity;
            if(ownerId == ball.getOwnerId()) {
                stopMoving();
            }
            else {
                if(radius >= ball.radius) {
                    updateRadius(ball.getWeight());
                    ball.die();
                }
                else{
                    ball.updateRadius(getWeight());
                    die();
                }
            }
        }
        else {
            updateRadius(entity.getWeight());
            entity.die();
        }
    }

    public void updateRadius(float massGained){
        radius = (float) Math.sqrt(radius * radius + massGained / Math.PI);
        speed = 50/radius;
        listener.accept(PacketFactory.createRadiusPacket(id, radius));
    }
}
