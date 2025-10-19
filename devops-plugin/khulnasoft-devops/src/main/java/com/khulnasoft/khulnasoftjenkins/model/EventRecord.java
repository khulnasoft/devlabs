package com.khulnasoft.khulnasoftjenkins.model;

import java.util.Map;

/**
 * Represents a single event record to be sent to Khulnasoft
 */
public class EventRecord {
    private final Map<String, Object> data;
    private final EventType eventType;
    private String source;

    /**
     * Create an event record
     * @param data the event data
     * @param eventType the type of event
     */
    public EventRecord(Map<String, Object> data, EventType eventType) {
        this.data = data;
        this.eventType = eventType;
    }

    /**
     * Create an event record with string data
     * @param data the event data as string
     * @param eventType the type of event
     */
    public EventRecord(String data, EventType eventType) {
        this.data = Map.of("message", data);
        this.eventType = eventType;
    }

    /**
     * Get the event data
     * @return the event data map
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Get the event type
     * @return the event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Get the source of this event
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Set the source of this event
     * @param source the source
     */
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "EventRecord{" +
                "eventType=" + eventType +
                ", source='" + source + '\'' +
                ", data=" + data +
                '}';
    }
}
