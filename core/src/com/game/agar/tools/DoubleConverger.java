package com.game.agar.tools;

public class DoubleConverger extends Converger<Double> {

    public DoubleConverger(Double first, int convergenceTime) {
        super(first, convergenceTime);
    }

    @Override
    public Double getDifference() {
        return Math.abs(this.convergenceValue - this.originalValue);
    }

    @Override
    public Double getStep(double part) {
        return getDifference() / part;
    }

    @Override
    public void convergeBy(Double amount) {
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
