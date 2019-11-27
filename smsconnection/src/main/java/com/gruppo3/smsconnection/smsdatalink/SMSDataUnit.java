package com.gruppo3.smsconnection.smsdatalink;


import com.gruppo3.smsconnection.connection.ProtocolDataUnit;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

import java.io.UnsupportedEncodingException;

/**
 * @author Mattia Fanan
 * sms implementation of abstract message
 */
public class SMSDataUnit implements ProtocolDataUnit<SMSPeer,byte[]> {

    SMSProtocolControlInformation PCI;
    SMSServiceDataUnit SDU;

    /**
     * build the message
     */
    public SMSDataUnit(SMSPeer destination, SMSPeer source, byte[] data)
            throws InvalidPeerException, InvalidPayloadException {
        SDU=new SMSServiceDataUnit(data);
        PCI = new SMSProtocolControlInformation(destination, source, SDU);
    }

    /**
     * get a SMSDataUnit from a SDU passed from lower layer
     * @param sourceAddress source phone number
     * @param LowerLayerSDU SDU containing data
     * @return SMSDataUnit if is possible to extract one from the SDU
     * @throws InvalidPeerException
     * @throws InvalidPayloadException
     */
    //if we can manipulate SmaMessage by pdu this method doesn't need peer address
    public static SMSDataUnit buildFromSDU(String sourceAddress,byte[] LowerLayerSDU)
            throws InvalidPeerException,InvalidPayloadException {
        byte[] byteStamp=new byte[4];
        byte[] byteLength=new byte[4];
        System.arraycopy(LowerLayerSDU, 0, byteStamp, 0, 4);
        System.arraycopy(LowerLayerSDU, 4,  byteLength, 0, 4);

        try{
            //from min_max to 0_length
            short b=(short)(
                    (short)new String(byteLength, "UTF-16").charAt(0)-Short.MIN_VALUE
            );
            //retrieve controlStamp
            String controlStamp=new String(byteStamp, "UTF-16");

            if(controlStamp.compareTo(SMSProtocolControlInformation.controlStamp)!=0)
                return null;

            byte[] byteData=new byte[b];
            System.arraycopy(LowerLayerSDU, 8,byteData , 0, b);

            return new SMSDataUnit(null,new SMSPeer(sourceAddress),byteData);
        }
        catch (UnsupportedEncodingException e){}

        return null;
    }

    /**
     * get a string of the data unit's properties
     * @return String
     */
    @Override
    public String toString()
    {
        String stringRapp="Message:"+SDU.getData();
        SMSPeer destination=PCI.getDestinationPeer();
        SMSPeer source=PCI.getSourcePeer();

        if(destination!=null)
            stringRapp=stringRapp+" ---Destination:"+destination.getAddress();
        if(source!=null)
            stringRapp=stringRapp+" ---Source:"+source.getAddress();

        return stringRapp;
    }

    /**
     * @return source peer in the ProtocolControlInformation
     */
    @Override
    public SMSPeer getSourcePeer() {
        return PCI.getSourcePeer();
    }

    /**
     * @return destination peer in the ProtocolControlInformation
     */
    @Override
    public SMSPeer getDestinationPeer() {
        return PCI.getDestinationPeer();
    }

    /**
     * @return ProtocolDataUnit's ServiceDataUnit to pass in the next Protocol
     */
    @Override
    public byte[] getSDU() {
        int PCILength=PCI.getToAddHeader().length;
        int SDULength=+SDU.getData().length;
        byte[] toReturnSDU=new byte[PCILength+SDULength];

        System.arraycopy(PCI.getToAddHeader(), 0, toReturnSDU, 0, PCILength);
        System.arraycopy(SDU.getData(),0 , toReturnSDU , PCILength, SDULength);

        return toReturnSDU;
    }

    /**
     *
     * @return
     */
    @Override
    public byte[] getData() {
        return SDU.getData();
    }
}
