package com.game.agarserver.logic;

import com.game.agar.shared.Position;

public class Food extends Entity {

    public Food(World world, Position position, double radius) {
        super(world, position, radius);
    }

    public Food(Position position, double radius) {
        super(position, radius);
    }
}
