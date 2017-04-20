package com.game.agarserver.eventsystem;

import com.game.agarserver.eventsystem.events.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;


public class EventHandler {

    private HashMap<Class<Event>, Method> classToMethodMap = new HashMap<>();

    public EventHandler(){
        load();
    }

    private void load(){
        Stream<Method> methodStream = Arrays.stream(this.getClass().getMethods()).filter(
                method -> method.isAnnotationPresent(SubscribeEvent.class)
        );
        methodStream.forEach(method -> {
            if(method.getParameterCount() != 1){
                throw new RuntimeException("Metoda musi przyjmować 1 parametr");
            }
            Class parameterClass= method.getParameterTypes()[0];
            if(!Event.class.isAssignableFrom(parameterClass)){
                throw new RuntimeException("Parametr metody musi dziedziczyć po klasie Event");
            }
            method.setAccessible(true);
            if(!method.isAccessible()){
                throw new RuntimeException("Metoda musi być publiczna");
            }
            classToMethodMap.put(parameterClass, method);
        });
    }

    public final void sendEvent(Event event){
        Class eventClass = event.getClass();
        Method method = classToMethodMap.get(eventClass);
        if(method != null){
            try {
                method.invoke(this, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
