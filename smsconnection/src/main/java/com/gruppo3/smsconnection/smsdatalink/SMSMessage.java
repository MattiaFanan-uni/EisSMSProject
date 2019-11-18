package com.gruppo3.smsconnection.smsdatalink;

import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.connection.Message;
/**
 * @author Mattia Fanan
 * sms implementation of payloadData
 */
public class SMSMessage extends Message<String> {
    public static final int MAX_PAYLOAD_LENGTH=160;
    DataLinkProtocol protocol=DataLinkProtocol.DirectSend;

    /**
     * build the smsPayload
     * @param data
     * @throws InvalidDataException if a non valid data is passed
     */
    public SMSMessage(String data) throws InvalidDataException {
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

    public void setProtocol(DataLinkProtocol protocol) {
        this.protocol=protocol;
    }

    public DataLinkProtocol getProtocol() {
        return protocol;
    }

    public enum DataLinkProtocol{
        DirectSend
    }
}
