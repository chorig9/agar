package com.game.agarserver.eventsystem;


import com.game.agarserver.eventsystem.events.Event;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class EventProcessor {

    private Map<Class<? extends Event>, List<Consumer<Event>>> eventHandlers = new HashMap<>();

    public EventProcessor() { }

    public void addEventHandler(Class<? extends Event> eventType, Consumer<Event> eventHandler){
        if(!eventHandlers.containsKey(eventType))
            eventHandlers.put(eventType, new ArrayList<>());

        eventHandlers.get(eventType).add(eventHandler);
    }

    public void issueEvent(Event event){
        event.setEventProcessor(this);
        eventHandlers.get(event.getClass()).forEach(eventHandler -> eventHandler.accept(event));
    }


}
