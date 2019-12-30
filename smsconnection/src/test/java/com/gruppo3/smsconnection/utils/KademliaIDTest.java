package com.gruppo3.smsconnection.utils;

import android.util.Base64;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

import static org.mockito.Mockito.*;

public class KademliaIDTest {

    private static String addressOne = "11223344";
    private static String addressTwo = "12345678";
    private static String expectedHashedAddressOne = "T58QswTP6bKxH8sTh/aU4Y8I6jU=";
    private static byte[] expectedBytesAddressOne = new byte[]{79,-97,16,-77,4,-49,-23,-78,-79,31,-53,19,-121,-10,-108,-31,-113,8,-22,53,-116,126,-97,86,116,52,-45,-83,108,-67,127,-60};
    private SMSPeer peerOne;
    private SMSPeer peerTwo;
    private Base64 mockBase64;
    private final static int VALID_LENGTH = 20;
    private final static int ANOTHER_LENGTH = 10;
    private final static int NOT_VALID_LENGTH = -10;
    private final static String expectedXorDistance = "918577119906024823139507659158657059931202283177";
    //Failed test messages
    private static String shouldNotThrowException = "An exception has been wrongly thrown";
    private static String digestionDidntWork = "The hashing didn't work properly";
    private static String lengthShouldBeValid = "The entered length should not be considered invalid";
    private static String lengthShouldBeInvalid = "The entered length should not be considered valid";

    @Test
    public void constructorWithGivenPeerAndLengthTest() throws InvalidPeerException {
        try {
            peerOne = new SMSPeer(addressOne);
            KademliaID kadID = new KademliaID(peerOne, VALID_LENGTH);
        } catch (InvalidLengthException ile) {
            Assert.fail(shouldNotThrowException);
        }
    }


    @Test
    public void digestBytesTest() {
        String algorithm = "SHA-256";
        try{
            peerOne = new SMSPeer(addressOne);
            peerTwo = new SMSPeer(addressTwo);
        } catch(InvalidPeerException ipe){
            ipe.printStackTrace();
        }
        //tests if two different arrays of bytes are differently digested.
        byte[] bytesOne = peerOne.getAddress().getBytes();
        byte[] bytesTwo = peerTwo.getAddress().getBytes();
        byte[] digestedBytesOne = KademliaID.digestBytes(bytesOne, algorithm);
        byte[] digestedBytesTwo = KademliaID.digestBytes(bytesTwo, algorithm);
        if(arraysAreEquals(digestedBytesOne, digestedBytesTwo)){
            Assert.fail(digestionDidntWork);
        }

        //tests if two equal array of bytes are digested in the same way.
        byte[] bytesOneClone = new byte[bytesOne.length];
        for(int i = 0; i < bytesOne.length; i++){
            bytesOneClone[i] = bytesOne[i];
        }
        byte[] digestedBytesOneClone = KademliaID.digestBytes(bytesOneClone, algorithm);
        if(!arraysAreEquals(digestedBytesOne, digestedBytesOneClone)){
            Assert.fail(digestionDidntWork);
        }
    }

    /**
     * This method is used to verify if two arrays of bytes of the same length are equals.
     * @param a an array of bytes of the same length of b.
     * @param b an array of bytes of the same length of a.
     * @return true if the two arrays are equals, false otherwise.
     */
    private boolean arraysAreEquals( byte[] a, byte[] b ){
        boolean flag = true;
        for(int i = 0; i < a.length; i++){

            if(a[i] != b[i]){
                flag = false;
                break;
            }
        }
        return flag;
    }

    @Test
    public void lengthIsValidTest() throws InvalidPeerException, InvalidLengthException {
        peerOne = new SMSPeer(addressOne);
        KademliaID id = new KademliaID(peerOne, VALID_LENGTH);

        if(!KademliaID.lengthIsValid(VALID_LENGTH)){
            Assert.fail(lengthShouldBeValid);
        }

        if(KademliaID.lengthIsValid(ANOTHER_LENGTH)){
            Assert.fail(lengthShouldBeInvalid);
        }

        if(KademliaID.lengthIsValid(NOT_VALID_LENGTH)){
            Assert.fail(lengthShouldBeInvalid);
        }
    }

    @Test
    public void getIDinBytesTest() throws InvalidPeerException, InvalidLengthException {
        peerOne = new SMSPeer(addressOne);
        KademliaID id = new KademliaID(peerOne, VALID_LENGTH);
        for(int i = 0; i < VALID_LENGTH; i++ ){
            if(id.getIDinBytes()[i] != expectedBytesAddressOne[i]){
                Assert.fail(digestionDidntWork);
            }
        }
    }

    @Test
    public void getIDinStringTest() throws InvalidPeerException, InvalidLengthException {
        peerOne = new SMSPeer(addressOne);
        KademliaID id = new KademliaID(peerOne, VALID_LENGTH);
        when(mockBase64.encodeToString (id.getIDinBytes(), Base64.DEFAULT)).thenReturn(expectedHashedAddressOne);
        Assert.assertEquals(expectedHashedAddressOne, mockBase64.encodeToString (id.getIDinBytes(), Base64.DEFAULT));
    }

    @Test
    public void xorDistanceTest() throws InvalidPeerException, InvalidLengthException{
        peerOne = new SMSPeer(addressOne);
        peerTwo = new SMSPeer(addressTwo);
        KademliaID id1 = new KademliaID(peerOne, VALID_LENGTH);
        KademliaID id2 = new KademliaID(peerTwo, VALID_LENGTH);
        BigInteger distance = id1.xorDistanceFrom(id2);
        String toCompare = distance.toString();
        Assert.assertEquals(toCompare, expectedXorDistance);
    }
}