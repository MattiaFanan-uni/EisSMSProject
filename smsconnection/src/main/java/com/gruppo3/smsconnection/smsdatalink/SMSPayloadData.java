package com.gruppo3.smsconnection.smsdatalink;

import com.gruppo3.smsconnection.connection.Exceptions.InvalidDataException;
import com.gruppo3.smsconnection.connection.PayloadData;

public class SMSPayloadData extends PayloadData<String> {
    public static final int MAX_PAYLOAD_LENGTH=160;

    public SMSPayloadData(String data) throws InvalidDataException {
        super(data);
    }

    @Override
    protected boolean isValidData(String data) {
        return data!=null && data.length()<=MAX_PAYLOAD_LENGTH;
    }

}
