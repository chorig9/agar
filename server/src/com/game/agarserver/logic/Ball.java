package com.game.agarserver.logic;

public class Ball extends Entity{

    private static long next_id = 0;

    private long id;
    private long ownerId;
    private float moveAngle;
    private float speed;

    private Position force = new Position(0, 0);

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
        force.x = force.y = 0;
    }

    public Position getVector(){
        float x = (float)Math.cos(moveAngle) * speed + force.x;
        float y = (float)Math.sin(moveAngle) * speed + force.y;

        return new Position(x, y);
    }

    public Position getNextPosition(){
        return new Position(position.x + getVector().x, position.y + getVector().y);
    }

    public void move(){
        position = getNextPosition();
        force.x = force.y = 0;

        listener.accept(PacketFactory.createPositionPacket(id, position));
    }

    public boolean isCollision(Entity collidingObject){
        double dx = getNextPosition().x - collidingObject.position.x;
        double dy = getNextPosition().y - collidingObject.position.y;
        double distance = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
        return distance < this.radius + collidingObject.radius;
    }

    Position vectorProjection(Position a, Position b){
        float multi = a.x * b.x + a.y * b.y;
        float length = b.x * b.x + b.y * b.y;
        return new Position(b.x * multi / length, b.y * multi / length);
    }

    private float dist(Position p1, Position p2){
        return (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public void handleCollision(Entity entity){
        if(entity instanceof Ball) {
            Ball ball = (Ball) entity;
            if(speed >= ball.speed) {
                if (ownerId == ball.getOwnerId()) {
                    // if ball is getting away
                    if(dist(getNextPosition(), ball.position) > dist(position, ball.position))
                        return;
                    
                    //Position outsideForce = new Position(- position.y + ball.position.y, position.x - ball.position.x);
                    //Position p = vectorProjection(moveVector, outsideForce);
                    //moveVector.x += ball.moveVector.x;
                    //moveVector.y += ball.moveVector.y;

                    // od wektora prekości kulki odejmuję prędkość w kierunku kolizyjnej kulki (rzut na prostą)
                    Position pull = new Position(position.x - ball.position.x, position.y - ball.position.y);
                    Position toBall = vectorProjection(getVector(), pull);
                    Position fromBall = vectorProjection(ball.getVector(), pull);
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

    public void updateRadius(float massGained){
        radius = (float) Math.sqrt(radius * radius + massGained / Math.PI);
        speed = 50/radius;
        setMoveAngle(moveAngle);
        listener.accept(PacketFactory.createRadiusPacket(id, radius));
    }
}
