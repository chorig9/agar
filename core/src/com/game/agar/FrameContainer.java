package com.game.agar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FrameContainer {

    private List<Position> objects = Collections.synchronizedList(new ArrayList<>());
    private int width, height;

    public FrameContainer(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void translate(double x, double y){
        for(Iterator<Position> it = objects.iterator(); it.hasNext();){
            Position object = it.next();
            object.x += x;
            object.y += y;

            if(!isInBounds(object))
                it.remove();
        }
    }

    public void addObjectAt(Position position){
        objects.add(position);
    }

    public void removeObjectAt(Position position){
        objects.removeIf(p -> p.equals(position));
    }

    public final List<Position> getObjects(){
        return objects;
    }

    private boolean isInBounds(Position position){
        return position.x >= 0 && position.y >= 0 && position.x < width && position.y < height;
    }

}
