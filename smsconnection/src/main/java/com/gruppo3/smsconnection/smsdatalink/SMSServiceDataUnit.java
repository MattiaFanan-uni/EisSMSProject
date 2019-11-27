package com.gruppo3.smsconnection.smsdatalink;

import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.ServiceDataUnit;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;

/**
 * @author Mattia Fanan
 * used to store data in SMSDataUnit
 */
public class SMSServiceDataUnit implements ServiceDataUnit<byte[]> {

    private byte[] data;
    
    /**
     * build the smsPayload
     * @param data Payload's data
     * @throws InvalidPayloadException if a non valid data is passed
     */
    public SMSServiceDataUnit(@NonNull byte[] data) throws InvalidPayloadException {
        if(!isValidData(data))
            throw new InvalidPayloadException();
        this.data=data;
    }

    

    /**
     * method that decides what is a valid data for the payload
     * @param data to validate
     * @return true if data is not null and shorther than MAX_PAYLOAD_LENGTH
     */
    private boolean isValidData(@NonNull byte[] data) {
        return data!=null && data.length <= SMSProtocolControlInformation.MAX_PAYLOAD_LENGTH;
    }


    /**
     * Get data of the ServiceDataUnit
     *
     * @return data of the payload
     */
    @Override
    @NonNull
    public byte[] getData() {
        return data;
    }

    /**
     * get the payload's size
     *
     * @return positive integer payload's size
     */
    @Override
    public int getSize() {
        return data.length;
    }
}
