package com.game.agarserver.eventsystem.events;

import com.game.agarserver.eventsystem.EventProcessor;

public abstract class Event {
    private EventProcessor eventProcessor;

    public Event() {
    }

    public EventProcessor getEventProcessor() {
        return eventProcessor;
    }

    public void setEventProcessor(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }
}
