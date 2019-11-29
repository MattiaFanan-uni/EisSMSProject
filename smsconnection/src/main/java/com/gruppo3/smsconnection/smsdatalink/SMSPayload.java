package com.gruppo3.smsconnection.smsdatalink;

import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.Payload;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;

/**
 * @author Mattia Fanan
 * data-link sms's payload
 */
public class SMSPayload implements Payload<byte[]> {

    private byte[] data;
    
    /**
     * @param data  data to store in payload
     * @throws InvalidPayloadException if non valid data is found
     */
    public SMSPayload(@NonNull byte[] data) throws InvalidPayloadException {
        if(!isValidData(data))
            throw new InvalidPayloadException();
        this.data=data;
    }

    

    /**
     * method that decides what is a valid data for the payload
     * @param data payload's data to validate
     * @return <code>true</code> if data is shorter than MAX_PAYLOAD_LENGTH
     */
    private boolean isValidData(@NonNull byte[] data) {
        return data.length <= SMSMessage.MAX_PAYLOAD_LENGTH;
    }


    /**
     * get payload's data
     * @return data of the payload
     */
    @Override
    @NonNull
    public byte[] getData() {
        return data;
    }

    /**
     * get payload's size
     * @return positive integer payload's size
     */
    @Override
    public int getSize() {
        return data.length;
    }
}
