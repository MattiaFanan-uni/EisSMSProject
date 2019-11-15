package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidHeaderException;

/**
 * @author Mattia Fanan
 * abstraction of DataUnit
 * @param H message's header type
 * @param M message's payload type
 */
public abstract class DataUnit<H extends Header,M extends Payload> {
    protected H header;
    protected M payload;

    /**
     * build the message
     * @param header header
     * @param payload payload
     * @throws InvalidHeaderException when invalid peer passed
     * @throws InvalidPayloadException when invalid payload passed
     */
    public DataUnit(H header, M payload)throws InvalidHeaderException, InvalidPayloadException {
        if(header==null || !header.isValid())
            throw new InvalidHeaderException();
        if(payload==null || !payload.isValid())
            throw new InvalidPayloadException();
        this.payload=payload;
        this.header=header;
    }

    /**
     * @return D the payloadData contained in the message
     */
    public M getPayload(){ return payload; }



    /**
     * set message's payload if a valid one is passed
     * @param payload message's payload
     * @return true if the message's payload is valid
     */
    public boolean setPayload(M payload){
        if(!payload.isValid())
            return false;
        this.payload=payload;
        return true;
    }

    /**
     * get the data unit's header
     * @return header
     */
    public H getHeader() {return header;}

    /**
     * set the data unit's header
     * @param header
     * @return
     */
    public boolean setHeader(H header){
        if(header==null || !header.isValid())
            return false;
        this.header=header;
        return true;
    }

    /**
     * method for check message's validity
     * @return true if is valid
     */
    public boolean isValid(){
        return header.isValid()&& payload.isValid();
    }
}

