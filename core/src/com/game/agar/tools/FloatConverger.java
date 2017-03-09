package com.game.agar.tools;

public class FloatConverger extends Converger<Float> {

    public FloatConverger(Float first, int convergenceTime) {
        super(first, convergenceTime);
    }

    @Override
    public Float getDifference() {
        return Math.abs(this.convergenceValue - this.originalValue);
    }

    @Override
    public Float getStep(float part) {
        return getDifference() / part;
    }

    @Override
    public void convergeBy(Float amount) {
        int sign = convergenceValue > originalValue ? 1 : -1;

        if(getDifference() > amount)
            originalValue += sign * amount;
        else
            originalValue = convergenceValue;
    }

    @Override
    public boolean isConverged() {
        return getDifference() < ACCURACY;
    }

}
