package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
/**
 * @author Mattia Fanan
 * abstraction of message's payload
 * @param T typer of data in the payload
 */
public abstract class PayloadData<T> {
    protected T data;

    /**
     * build the payload
     * @param data payload's data
     * @throws InvalidDataException when not valid payload is passed
     */
    public PayloadData(T data)throws InvalidDataException {
        if(!isValidData(data))
            throw new InvalidDataException();
        this.data=data;
    }

    /**
     *
     * @return payload's data
     */
    public T getData(){
        return data;
    }

    /**
     * set payload's data if a valid one is passed
     * @param data payload's data
     * @return true if payload's data setted correctly
     */
    public boolean setData(T data){
        if(!isValidData(data))
            return false;
        this.data=data;
        return true;
    }

    /**
     * method that decides what is a valid data for the payload
     * @param data to validate
     * @return true if is valid
     */
    protected abstract boolean isValidData(T data);

    /**
     * check if payload is valid
     * @return true if is valid
     */
    public boolean isValid(){
        return isValidData(data);
    }
}
