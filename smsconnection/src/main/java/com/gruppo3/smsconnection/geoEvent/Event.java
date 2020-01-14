package com.gruppo3.smsconnection.geoEvent;

public interface Event<T> {
    /**
     * @return The details of this point.
     */
    T getContent();

    /**
     * @return The position of this point.
     */
    GPSPosition getPosition();
}
