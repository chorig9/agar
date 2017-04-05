package com.game.agar.tools;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Converger<T> {

    final double ACCURACY = 0.0001f;
    final int TIME_STEP = 5;
    final int TIME_ADJUSTMENT = 10;

    T originalValue, convergenceValue;
    private int convergenceTime;
    private volatile long time;
    private T step;
    private boolean converged = false;

    public Converger(T originalValue, int convergenceTime){
        this.originalValue = originalValue;
        this.convergenceTime = convergenceTime;

        convergenceValue = originalValue;
        step = getStep(1);
        TimerTask growth = new TimerTask() {
            @Override
            public void run() {
                if(!isConverged()) {
                    convergeBy(step);
                    if(isConverged()){
                        time = System.currentTimeMillis();
                    }
                }
            }
        };

        new Timer().schedule(growth, 0, TIME_STEP);
    }

    public void doConverge(T destinationValue){
        if(!isConverged())                               // if value had not been converged before new value came
            convergenceTime -= TIME_ADJUSTMENT;          // adjust convergenceTime
        else if(System.currentTimeMillis() - time > 15)  // if convergence had been done before new value came (long enough)
            convergenceTime += TIME_ADJUSTMENT;          // adjust convergenceTime

        this.convergenceValue = destinationValue;
        step = getStep(convergenceTime / TIME_STEP);
    }

    public abstract T getDifference();

    public abstract T getStep(double part);

    public abstract void convergeBy(T amount);

    public abstract boolean isConverged();

    public T getValue(){
        return originalValue;
    }

}
