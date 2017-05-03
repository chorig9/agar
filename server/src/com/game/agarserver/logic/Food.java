package com.game.agarserver.logic;

import com.game.agarserver.tools.Vector;

public class Food extends Entity {

    public Food(World world, Vector position, double radius) {
        super(world, position, radius);
    }

    public Food(Vector position, double radius) {
        super(position, radius);
    }
}
