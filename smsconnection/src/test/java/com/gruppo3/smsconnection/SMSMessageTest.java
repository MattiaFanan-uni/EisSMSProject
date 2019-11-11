package com.gruppo3.smsconnection;

import com.gruppo3.smsconnection.smsdatalink.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SMSMessageTest {
    private String fullOfAStringGenerator(int length) {
        char[] s = new char[length];
        for (int i = 0; i < s.length; i++)
            s[i] = 'a';
        return new String(s);
    }

    @Test
    public void addHeaderTrue() {
        String header = "C";
        String message = fullOfAStringGenerator(SMSMessage.MAX_LENGHT - 1);
        boolean expected = true;
        assertEquals(expected, new SMSMessage(new SMSPeer("12345"), message).addHeader(header));
    }

    @Test
    public void addHeaderFalse() {
        String header = "C";
        String message = fullOfAStringGenerator(SMSMessage.MAX_LENGHT);
        boolean expected = false;
        assertEquals(expected, new SMSMessage(new SMSPeer("12345"), message).addHeader(header));
    }

    @Test
    public void addHeaderExact() {
        String header = "C";
        String message = fullOfAStringGenerator(SMSMessage.MAX_LENGHT - 1);
        assertEquals(header + message, new SMSMessage(new SMSPeer("12345"), message).getData());
    }

    @Test
    public void setPeerValid() {
        SMSPeer peer = new SMSPeer("33454");
        boolean expected = true;
        SMSMessage sms = new SMSMessage(new SMSPeer("12345"), "sdfg");
        sms.setPeer(peer);
        assertEquals(expected, peer.equals(sms.getPeer()));
    }

    @Test
    public void setPeerNonValid() {
        SMSPeer peer = new SMSPeer("34");
        boolean expected = false;
        SMSMessage sms = new SMSMessage(new SMSPeer("12345"), "sdfg");
        sms.setPeer(peer);
        assertEquals(expected, peer.equals(sms.getPeer()));
    }


    @Test
    public void setDataValid() {
        String data = fullOfAStringGenerator(65);
        boolean expected = true;
        SMSMessage sms = new SMSMessage(new SMSPeer("12345"), "sdfg");
        sms.setData(data);
        assertEquals(expected, data == sms.getData());
    }

    @Test
    public void setDataNull() {
        String data = null;
        boolean expected = true;
        SMSMessage sms = new SMSMessage(new SMSPeer("12345"), "sdfg");
        sms.setData(data);
        assertEquals(expected, "sdfg" == sms.getData());
    }

    @Test
    public void setDataOverMax() {
        String data = fullOfAStringGenerator(SMSMessage.MAX_LENGHT + 1);
        boolean expected = false;
        SMSMessage sms = new SMSMessage(new SMSPeer("12345"), "sdfg");
        sms.setData(data);
        assertEquals(expected, data == sms.getData());
    }

    @Test
    public void setDataMax() {
        String data = fullOfAStringGenerator(SMSMessage.MAX_LENGHT);
        boolean expected = false;
        SMSMessage sms = new SMSMessage(new SMSPeer("12345"), "sdfg");
        sms.setData(data);
        assertEquals(expected, data == sms.getData());
    }


    @Test
    public void isValidOverMax() {

    }

    @Test
    public void isValidNull() {

    }

    @Test
    public void isValid() {

    }

    @Test
    public void isValidMax() {

    }


    @Test
    public void getData() {
        String expected = "qwerty";
        assertEquals(true, expected== new SMSMessage(new SMSPeer("12345"), expected).getData());
    }
}