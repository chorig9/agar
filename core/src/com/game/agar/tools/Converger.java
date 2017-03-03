package com.game.agar.tools;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Converger<T> {

    T originalValue, convergenceValue;
    final float ACCURACY = 0.0001f;
    private final int TIME_STEP = 1;

    public Converger(T originalValue, T convergenceValue){
        this.originalValue = originalValue;
        this.convergenceValue = convergenceValue;
    }
    
    public void setConvergenceTo(T convergenceValue){
        this.convergenceValue = convergenceValue;
    }

    public void convergeNow(){
        originalValue = convergenceValue;
    }

    public void doConvergeFully(float duration){
        T step = getStep(duration / TIME_STEP);
        Timer timer = new Timer();
        TimerTask growth = new TimerTask() {
            @Override
            public void run() {
                if(isConverged()) {
                    timer.cancel();
                    timer.purge();
                }
                else {
                    convergeBy(step);
                }
            }
        };
        timer.schedule(growth, 0, TIME_STEP);
    }

    public abstract T getDifference();

    public abstract T getStep(float part);
    
    public abstract void convergeBy(T amount);

    public abstract boolean isConverged();

    public T getValue(){
        return originalValue;
    }
    
}
