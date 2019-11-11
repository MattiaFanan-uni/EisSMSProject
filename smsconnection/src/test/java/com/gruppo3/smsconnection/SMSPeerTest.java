package com.gruppo3.smsconnection;

import com.gruppo3.smsconnection.smsdatalink.SMSPeer;

import org.junit.Test;

import static org.junit.Assert.*;
public class SMSPeerTest {
    @Test
    public void testIsValidMid(){
        boolean expected=true;
        assertEquals(expected,new SMSPeer("1234567").isValid());
    }
    @Test
    public void testIsValidUpper(){
        boolean expected=false;
        assertEquals(expected,new SMSPeer("1342567890164532567").isValid());
    }
    @Test
    public void testIsValidLower(){
        boolean expected=false;
        assertEquals(expected,new SMSPeer("67").isValid());
    }
    @Test
    public void testIsValidMax(){
        boolean expected=true;
        assertEquals(expected,new SMSPeer("123456789023456").isValid());
    }
    @Test
    public void testIsValidMin(){
        boolean expected=true;
        assertEquals(expected,new SMSPeer("4567").isValid());
    }
    @Test
    public void testIsValidLitteral(){
        boolean expected=false;
        assertEquals(expected,new SMSPeer("1234s567").isValid());
    }


    @Test
    public void getAddress(){
        String expected="1234567";
        assertEquals(true,expected==new SMSPeer(expected).getAddress());
    }
}
