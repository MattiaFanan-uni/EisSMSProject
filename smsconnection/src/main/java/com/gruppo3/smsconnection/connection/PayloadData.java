package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.connection.exceptions.InvalidDataException;

public abstract class PayloadData<T> {
    protected T data;

    public PayloadData(T data)throws InvalidDataException {
        if(!isValidData(data))
            throw new InvalidDataException();
        this.data=data;
    }

    public T getData(){
        return data;
    }

    public boolean setData(T data){
        if(!isValidData(data))
            return false;
        this.data=data;
        return true;
    }

    protected abstract boolean isValidData(T data);

    public boolean isValid(){
        return isValidData(data);
    }
}
