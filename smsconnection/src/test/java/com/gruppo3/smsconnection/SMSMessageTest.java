package com.gruppo3.smsconnection;

import com.gruppo3.smsconnection.connection.Message;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import  com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.smsdatalink.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.SMSPayloadData;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;


import static org.junit.Assert.*;

public class SMSMessageTest {

    SMSMessage message;

    @Test
    public void setUp(){
        try {
            message = new SMSMessage(new SMSPeer("390000000000000"), new SMSPayloadData("test"));
        }
        catch (Exception e){Assert.fail("Should not throw an exception");}
    }

    @Test
    public void numberIsNotTooLong() {
        try {
            message = new SMSMessage(new SMSPeer("3900000000000000"), new SMSPayloadData("test"));
            Assert.fail("Should throw InvalidPeerException ");
        }
        catch (InvalidPeerException e) {} //correct
        catch (Exception e) {Assert.fail("Should throw InvalidPeerException");}
    }

    @Test
    public void textIsNotTooLong() {
        try {
            message = new SMSMessage(new SMSPeer("3900000000000"), new SMSPayloadData("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG"));
            Assert.fail("Should throw InvalidDataException");
        }
        catch (InvalidDataException e) {} //correct
        catch (Exception e) {Assert.fail("Should throw Invalid DataException");}
    }

    @Test
    public void numberHasNoLetters(){
        try {
            message = new SMSMessage(new SMSPeer("3900p0a00c0d0"), new SMSPayloadData("test"));
            Assert.fail("Should throw InvalidPeerException");
        }catch(InvalidPeerException e){
            //Correct
        }catch(Exception e){
            Assert.fail("Should throw InvalidPeerException");
        }
    }

    @Test
    public void hasSameNumber(){
        try {
            message = new SMSMessage(new SMSPeer("390000000000000"), new SMSPayloadData("test"));
        }catch(Exception e){
            Assert.fail("Should not throw an exception");
        }
        Assert.assertEquals(message.getPeer().getAddress(),"390000000000000");
    }

    @Test
    public void hasSameText(){
        try {
            message = new SMSMessage(new SMSPeer("390000000000000"), new SMSPayloadData("test"));
        }catch(Exception e){
            Assert.fail("Should not throw an exception");
        }
        Assert.assertEquals(message.getPayloadData().getData(),"test");
    }

    @Test
    public void isAddHeaderValid() {
        String header = "headerTest";
        try {
            message = new SMSMessage(new SMSPeer("390000000000000"), new SMSPayloadData("test"));
            message.addHeader(header);
        }
        catch (Exception e) {Assert.fail("Should not throw an exception");}
    }

    @Test
    public void toStringTest() {
        try {
            message = new SMSMessage(new SMSPeer("390000000000000"), new SMSPayloadData("test"));
        }
        catch(Exception e){Assert.fail("Should not throw an exception");}
        Assert.assertEquals(message.toString(), "SMSPeer: 390000000000000, SMSMessage: test");
    }
}