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
                throw new RuntimeException("Event handling method must take only 1 parameter");
            }
            Class parameterClass= method.getParameterTypes()[0];
            if(!Event.class.isAssignableFrom(parameterClass)){
                throw new RuntimeException("Event handling method parameter must be an instance of Event");
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
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }catch(InvocationTargetException e){
                throw new RuntimeException(e.getCause());
            }
        }
    }

}
