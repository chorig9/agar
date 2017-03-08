package com.game.agarserver.logic;

public class Player extends Entity{

    private static long next_id = 0;

    private long id;
    private float movingAngle;

    public Player(Position position, int radius){
        super(position, radius);
        id = (next_id++) % Long.MAX_VALUE;
    }

    public void setMovingAngle(float angle){
        movingAngle = angle;
    }

    public void move(float distance){
        position.x += Math.cos(movingAngle) * distance;
        position.y += Math.sin(movingAngle) * distance;

        listener.accept(PacketFactory.createPositionPacket(id, position));
    }

    public boolean isCollision(Entity collidingObject){
        double dx = this.position.x - collidingObject.position.x;
        double dy = this.position.y - collidingObject.position.y;
        double distance = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
        return distance < this.radius + collidingObject.radius;
    }

    public void handleCollision(Entity entity){
        if(radius > entity.radius){
            updateRadius(entity.getWeight());
            entity.die();
        }
        else if(entity instanceof Player){
            Player player = (Player) entity;
            player.updateRadius(getWeight());
            die();
        }
    }

    public void updateRadius(float massGained){
        radius = (float) Math.sqrt(radius * radius + massGained / Math.PI);
        listener.accept(PacketFactory.createRadiusPacket(id, radius));
    }

}
