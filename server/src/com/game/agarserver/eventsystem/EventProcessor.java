package com.game.agarserver.eventsystem;


import com.game.agarserver.eventsystem.events.Event;

import java.util.ArrayList;
import java.util.List;

public class EventProcessor {

    private List<EventHandler> eventHandlers = new ArrayList<>();

    public EventProcessor() {}

    public void addEventHandler(EventHandler eventHandler){
        eventHandlers.add(eventHandler);
    }

    public void issueEvent(Event event){
        event.setEventProcessor(this);
        eventHandlers.forEach(eventHandler -> eventHandler.sendEvent(event));
    }


}
