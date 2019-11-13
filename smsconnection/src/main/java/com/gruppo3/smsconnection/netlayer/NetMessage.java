package com.gruppo3.smsconnection.netlayer;

import com.gruppo3.smsconnection.connection.Message;
import com.gruppo3.smsconnection.connection.exception.InvalidDataException;

/**
 * @author Mattia Fanan
 */
public class NetMessage extends Message<String> {
    /**
     * build the payload
     *
     * @param data payload's data
     * @throws InvalidDataException when not valid payload is passed
     */
    public NetMessage(String data) throws InvalidDataException {
        super(data);
    }

    @Override
    protected boolean isValidData(String data) {
        return data!=null;
    }
}
