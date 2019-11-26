package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;

/**
 * Payload abstract class
 * @author Mattia Fanan
 *
 * @param T Payload data data-type
 */
public abstract class Payload<T> {
    protected T data;

    /**
     * Create a payload
     * @param data Data of the Payload
     * @throws InvalidPayloadException when an invalid data is passed
     */
    public Payload(T data)throws InvalidPayloadException {
        if(data==null || !isValidData(data))
            throw new InvalidPayloadException();

        this.data = data;
    }

    /**
     * Get data of the Payload
     * @return data of the payload
     */
    public T getData() {
        return data;
    }

    /**
     * Set data of the Payload
     * @param data Data to assign to the payload
     * @return true if data is valid, false if data is not valid
     */
    public boolean setData(T data) {
        if(data==null || !isValidData(data))
            return false;

        this.data = data;
        return true;
    }


    /**
     * Check if Payload's data is valid
     * @param data Payload's data to validate
     * @return true if data is valid, false if data is not valid
     */
    protected abstract boolean isValidData(T data);
}
