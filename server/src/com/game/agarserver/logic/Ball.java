package com.game.agarserver.logic;

public class Ball extends Entity{

    private static long next_id = 0;

    private long id;
    private long ownerId;
    private float moveAngle;
    private float speed;

    private Position moveVector = new Position(0, 0);

    public Ball(Position position, int radius, long ownerId){
        super(position, radius);
        this.ownerId = ownerId;
        this.id = (next_id++) % Long.MAX_VALUE;
        this.speed = 50/radius;
    }

    public long getId(){    return id;  }

    public long getOwnerId() {  return ownerId; }

    public void setMoveAngle(float angle){
        moveAngle = angle;
        moveVector.x = (float)Math.cos(moveAngle) * speed;
        moveVector.y = (float)Math.sin(moveAngle) * speed;
    }

    public void move(){
        position.x += moveVector.x;
        position.y += moveVector.y;
        setMoveAngle(moveAngle);
        listener.accept(PacketFactory.createPositionPacket(id, position));
    }

    public boolean isCollision(Entity collidingObject){
        double dx = position.x - collidingObject.position.x;
        double dy = position.y - collidingObject.position.y;
        double distance = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
        return distance <= this.radius + collidingObject.radius;
    }

    Position vectorProjection(Position a, Position b){
        float multi = a.x * b.x + a.y * b.y;
        float length = b.x * b.x + b.y * b.y;
        return new Position(b.x * multi / length, b.y * multi / length);
    }

    public void handleCollision(Entity entity){
        if(entity instanceof Ball) {
            Ball ball = (Ball) entity;
            if(speed >= ball.speed) {
                if (ownerId == ball.getOwnerId()) {
                    //Position outsideForce = new Position(- position.y + ball.position.y, position.x - ball.position.x);
                    //Position p = vectorProjection(moveVector, outsideForce);
                    //moveVector.x += ball.moveVector.x;
                    //moveVector.y += ball.moveVector.y;

                    // od wektora prekości kulki odejmuję prędkość w kierunku kolizyjnej kulki (rzut na prostą)
                    Position pull = new Position(position.x - ball.position.x, position.y - ball.position.y);
                    Position p = vectorProjection(moveVector, pull);
                    moveVector.x -=  (p.x - ball.moveVector.x);
                    moveVector.y -=  (p.y - ball.moveVector.y);

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

    public void updateRadius(float massGained){
        radius = (float) Math.sqrt(radius * radius + massGained / Math.PI);
        speed = 50/radius;
        setMoveAngle(moveAngle);
        listener.accept(PacketFactory.createRadiusPacket(id, radius));
    }
}
