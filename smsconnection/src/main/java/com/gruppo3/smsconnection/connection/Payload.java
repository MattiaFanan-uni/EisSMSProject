package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;

/**
 * Payload abstract class
 * @author Mattia Fanan
 *
 * @param T Payload's data data-type
 */
public interface Payload<T> {

    /**
     * Get data of the Payload
     * @return data of the payload
     */
    T getData();

    /**
     * get the payload's size
     * @return positive integer payload's size
     */
    int getSize();
}
