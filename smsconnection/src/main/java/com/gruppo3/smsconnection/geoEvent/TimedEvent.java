package com.gruppo3.smsconnection.geoEvent;

/**
 * Represents an event with a timeout or a precise time.
 *
 * @param <T> Type of details for the event.
 */
public interface TimedEvent<T> extends Event<T> {
    /**
     * @return The time of the event.
     */
    DateTime getTime();
}
