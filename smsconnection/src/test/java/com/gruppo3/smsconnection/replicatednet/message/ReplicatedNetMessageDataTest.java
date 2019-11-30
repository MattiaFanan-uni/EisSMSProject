package com.gruppo3.smsconnection.replicatednet.message;

import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static com.gruppo3.smsconnection.utils.Utils.getAlphaNumericString;

public class ReplicatedNetMessageDataTest {

    byte[] validData;
    byte[] nullData=null;
    byte[] tooMuchData;
    byte[] maxData;


    public ReplicatedNetMessageDataTest(){
        try {
            //2 * 10 bytes + 2 endstring bytes = 22 bytes
            validData = getAlphaNumericString(10).getBytes("UTF-16");
            //2 * MAXPAYLOAD_LENGTH +2
            tooMuchData=getAlphaNumericString(ReplicatedNetMessage.MAX_PAYLOAD_LENGTH).getBytes("UTF-16");
            // 2 * ((MAXPAYLOAD_LENGTH/2 -2) +2) = MAXPAYLOAD_LENGTH
            maxData=getAlphaNumericString((ReplicatedNetMessage.MAX_PAYLOAD_LENGTH/2)-2).getBytes("UTF-16");
        }
        catch (UnsupportedEncodingException e){}
    }

    @Test
    public void setUp(){
        ReplicatedNetMessage message;
        try {
            message=new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    validData);
        }
        catch (InvalidPayloadException e){
            Assert.fail("Should not throw InvalidPayloadException exception");}
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void maxData() {
        ReplicatedNetMessage message;
        try {
            message=new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    maxData);
        }
        catch (InvalidPayloadException e) { Assert.fail("Shouldn't throw InvalidPeerException ");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void tooMuchData() {
        ReplicatedNetMessage message;
        try {
            message=new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    tooMuchData);
            Assert.fail("Should throw InvalidPeerException ");
        }
        catch (InvalidPayloadException e) {} //correct
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void nullData() {
        ReplicatedNetMessage message;
        try {
            message=new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    nullData);;
            Assert.fail("Should throw NullPointerException ");
        }
        catch (NullPointerException e) {} //correct
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void getDataTest() {
        ReplicatedNetMessage message;
        try {
            message=new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    validData);
            if( !Arrays.equals( message.getData(), validData))
                Assert.fail("should be the same data");
        }
        catch (InvalidPayloadException e) {Assert.fail("Shouldn't throw InvalidPayloadException");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

}