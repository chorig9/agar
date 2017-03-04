package com.game.agar.tools;

public class PositionConverger extends Converger<Position> {

    public PositionConverger(Position first, Position second) {
        super(first, second);
    }

    @Override
    public void convergeNow() {
        originalValue = convergenceValue.copy();
    }

    @Override
    public Position getDifference() {
        return new Position(Math.abs(convergenceValue.x - originalValue.x),
                            Math.abs(convergenceValue.y - originalValue.y));
    }

    @Override
    public Position getStep(float part) {
        Position difference = getDifference();
        difference.x /= part;
        difference.y /= part;

        return difference;
    }

    @Override
    public void convergeBy(Position amount) {
        // TODO better algorithm for position convergence
        int signX = convergenceValue.x > originalValue.x ? 1 : -1;
        int signY = convergenceValue.y > originalValue.y ? 1 : -1;

        if(getDifference().x > amount.x)
            originalValue.x += signX * amount.x;
        else
            originalValue.x = convergenceValue.x;

        if(getDifference().y > amount.y)
            originalValue.y += signY * amount.y;
        else
            originalValue.y = convergenceValue.y;
    }

    @Override
    public boolean isConverged() {
        return getDifference().x < ACCURACY && getDifference().y < ACCURACY;
    }
}
