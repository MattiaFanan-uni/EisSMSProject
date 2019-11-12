package com.gruppo3.smsconnection.smsdatalink;

import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.connection.PayloadData;
/**
 * @author Mattia Fanan
 * sms implementation of payloadData
 */
public class SMSPayloadData extends PayloadData<String> {
    public static final int MAX_PAYLOAD_LENGTH=160;

    /**
     * build the smsPayload
     * @param data
     * @throws InvalidDataException if a non valid data is passed
     */
    public SMSPayloadData(String data) throws InvalidDataException {
        super(data);
    }

    /**
     * method that decides what is a valid data for the payload
     * @param data to validate
     * @return true if data is not null and shorther than MAX_PAYLOAD_LENGTH
     */
    @Override
    protected boolean isValidData(String data) {
        return data!=null && data.length()<=MAX_PAYLOAD_LENGTH;
    }

}
