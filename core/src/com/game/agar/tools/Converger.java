package com.game.agar.tools;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Converger<T> {

    final float ACCURACY = 0.0001f;
    final int TIME_STEP = 5;
    final int TIME_ADJUSTMENT = 10;

    T originalValue, convergenceValue;
    private int convergenceTime;
    private volatile long time;
    private T step;

    public Converger(T originalValue, int convergenceTime){
        this.originalValue = originalValue;
        this.convergenceTime = convergenceTime;

        convergenceValue = originalValue;
        step = getStep(1);
        TimerTask growth = new TimerTask() {
            @Override
            public void run() {
                if(isConverged()) {
                    time = System.currentTimeMillis();
                }
                else {
                    convergeBy(step);
                }
            }
        };

        new Timer().schedule(growth, 0, TIME_STEP);
    }

    public void doConverge(T convergenceValue){
        if(!isConverged())                               // if value had not been converged before new value came
            convergenceTime -= TIME_ADJUSTMENT;          // adjust convergenceTime
        else if(System.currentTimeMillis() - time > 15)  // if convergence had been done before new value came (long enough)
            convergenceTime += TIME_ADJUSTMENT;          // adjust convergenceTime


        this.convergenceValue = convergenceValue;
        step = getStep(convergenceTime / TIME_STEP);
    }

    public abstract T getDifference();

    public abstract T getStep(float part);

    public abstract void convergeBy(T amount);

    public abstract boolean isConverged();

    public T getValue(){
        return originalValue;
    }

}
