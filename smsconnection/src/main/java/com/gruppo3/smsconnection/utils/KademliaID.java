package com.gruppo3.smsconnection.utils;

import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import android.util.Base64;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class provides an ID for a node in the Kademlia net and some useful functionalities.
 * @author Fabio Marangoni
 */
public class KademliaID implements Serializable {

    private static final String ALGORITHM_TYPE = "SHA-256";
    private static int ID_LENGTH = 0;
    private byte[] IDinBytes;

    /**
     * This is the constructor that provides an ID for a peer with the desired length.
     * @param peer the peer for which the user wants to generate its ID for the Kademlia net.
     * @param length the length (in Bytes) for the ID.
     */
    public KademliaID(SMSPeer peer, int length) throws InvalidLengthException{

        if(lengthIsValid(length)) {
            byte[] addressInBytes = peer.getAddress().getBytes();
            byte[] digestedBytes = digestBytes(addressInBytes, ALGORITHM_TYPE);
            ID_LENGTH = length;
            IDinBytes = new byte[ID_LENGTH];
            for (int i = 0; i < ID_LENGTH; i++) {
                IDinBytes[i] = digestedBytes[i];
            }
        }
        else{
            throw new InvalidLengthException();
        }
    }

    /**
     * This is the constructor that provides an ID for a peer. Use it only if a KademliaID instance already exists
     * (in this case ID_LENGTH is fixed and there is no need to pass again the length value to the constructor).
     * @param peer the peer for which the user wants to generate its ID for the Kademlia net.
     */
    public KademliaID(SMSPeer peer){
        byte[] addressInBytes = peer.getAddress().getBytes();
        byte[] digestedBytes = digestBytes(addressInBytes, ALGORITHM_TYPE);
        for (int i = 0; i < ID_LENGTH; i++) {
            IDinBytes[i] = digestedBytes[i];
        }
    }

    /**
     * This constructor generates a KademliaID starting from an array of bytes.
     * @param array the array of bytes that should represent the value (in bytes) of the ID.
     */
    protected KademliaID(byte[] array){
        for(int i = 0; i < ID_LENGTH; i++){
            IDinBytes[i] = array[i];
        }
    }

    /**
     * This method checks the validity of the ID length.
     * @param length the length in bytes of the Kademlia ID.
     * @return true if the length is valid, false otherwise.
     */
    protected static boolean lengthIsValid(int length){

        if (length <= 0 || length > 32){
            return false;
        }
        if(ID_LENGTH != 0 && ID_LENGTH != length){
            return false;  //the IDs must be coherent, so they should have the same length.
        }
        else{
            return true;
        }
    }

    /**
     * This method creates a MessageDigest object with a given hash algorithm and digests the given array of bytes.
     * @param toDigest the array of bytes for which the MessageDigest is created.
     * @param algorithm the type of hash algorithm (MD5, SHA-1, SHA-224, SHA-256, SHA-384, SHA-512).
     * @return the resulting array of bytes from the digest function. It returns null if the given hash algorithm is not valid.
     */
    protected static byte[] digestBytes (byte[] toDigest, String algorithm){

        try {
            MessageDigest newDigest = MessageDigest.getInstance(algorithm);
            newDigest.update(toDigest);
            byte[] digestedBytes = newDigest.digest();
            return digestedBytes;
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method gives the array of bytes representing the ID.
     * @return the ID in the form of an array of bytes.
     */
    protected byte[] getIDinBytes() {
        return this.IDinBytes;
    }

    /**
     * This method gives the string representing the ID.
     * @return the ID in the form of a string.
     */
    protected String getIDinString() {
        String IDinString = Base64.encodeToString (IDinBytes, Base64.DEFAULT);
        return IDinString;
    }

    /**
     * This method computes the xor-distance between two KademliaIDs.
     * @param otherID the KademliaID from which the distance is calculated.
     * @return a BigInteger that represents the xor-distance between the two KademliaIDs.
     */
    public BigInteger xorDistanceFrom (KademliaID otherID) {
        byte[] distance = new byte[ID_LENGTH];
        byte[] otherIdBytes = otherID.getIDinBytes();
        for (int i = 0; i < ID_LENGTH; i++) {
            distance[i] = (byte) (this.IDinBytes[i] ^ otherIdBytes[i]);
        }
        int sign = 1;
        return new BigInteger (sign, distance);
    }

}
