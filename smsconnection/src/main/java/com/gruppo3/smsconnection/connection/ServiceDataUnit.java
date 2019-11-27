package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;

/**
 * ServiceDataUnit abstract class
 * @author Mattia Fanan
 *
 * @param T ServiceDataUnit's data data-type
 */
public interface ServiceDataUnit<T> {

    /**
     * Get data of the ServiceDataUnit
     * @return data of the payload
     */
    T getData();

    /**
     * get the payload's size
     * @return positive integer payload's size
     */
    int getSize();
}
