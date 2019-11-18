package com.gruppo3.smsconnection.netlayer;

import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.exception.UnknownPeerException;
import com.gruppo3.smsconnection.smsdatalink.SMSDataUnit;

import java.util.HashMap;

/**
 * @author Mattia Fanan
 */
public class NetAdapter {
    SMSDataUnit smsDataUnit;
    NetDataUnit netDataUnit;

    /**
     * future net peer is inside message data
     * if i dont know peer add it
     * @param smsDataUnit
     * @param routingTable
     * @throws InvalidPeerException
     * @throws InvalidDataException
     * @throws UnknownPeerException
     */
    public NetAdapter(SMSDataUnit smsDataUnit, HashMap routingTable) throws InvalidPeerException,
            InvalidDataException
    {
        this.smsDataUnit=smsDataUnit;
        //check validity + exception


       // netDataUnit=new NetDataUnit(new NetPeer(smsDataUnit.getPeer()))
    }

    public NetDataUnit getNetDataUnit()
    {
        return netDataUnit;
    }

    /**
     * future
     * find a dictionary that works
     * @param netDataUnit
     * @param routingTable
     * @throws InvalidPeerException
     * @throws InvalidDataException
     * @throws UnknownPeerException
     */
    public NetAdapter(NetDataUnit netDataUnit, HashMap routingTable) throws
            InvalidPeerException, InvalidDataException,UnknownPeerException
    {
        this.netDataUnit=netDataUnit;
        //check validity + exception
        if(!routingTable.containsValue(smsDataUnit.getPeer().getAddress()))
            throw new UnknownPeerException();



       // this.smsDataUnit= new SMSDataUnit(new SMSPeer(routingTable.get(netDataUnit.getPeer())),new SMSMessage(smsText));
    }
    public SMSDataUnit getSMSDataUnit()
    {
        return smsDataUnit;
    }
}
