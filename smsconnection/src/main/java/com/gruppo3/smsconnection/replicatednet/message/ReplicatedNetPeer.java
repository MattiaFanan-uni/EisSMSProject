package com.gruppo3.smsconnection.replicatednet.message;

import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.Peer;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

/**
 * @author Mattia Fanan
 * <p>
 * net layer message's peer
 * it's address to be valid must be 16 bytes [128 bit] long
 */
public class ReplicatedNetPeer implements Peer<byte[]>, Comparable<ReplicatedNetPeer> {

    public static final int LENGTH = 16;//sha_1-->128 bit
    byte[] address;

    public ReplicatedNetPeer(byte[] address) throws InvalidPeerException {
        if (!isValidData(address))
            throw new InvalidPeerException();
        this.address = address;
    }

    private boolean isValidData(byte[] address) {
        return address.length == LENGTH;
    }

    /**
     * return the peer's address
     *
     * @return peer's address
     */
    @Override
    public byte[] getAddress() {
        return address.clone();
    }

    /**
     * compare the two addresses in 0_2^8-1 form byte
     * negatives are moved after Byte MAX-VALUE cause they start with 1
     *
     * @param replicatedNetPeer other peer to check
     * @return 0 if their addresses are equal,
     * -1 if the last not equal byte of this address is greater than replicatedNetPeer address,
     * 1 if the last not equal byte of this address is lower than replicatedNetPeer address
     */
    @Override
    public int compareTo(ReplicatedNetPeer replicatedNetPeer) {

        byte[] replicatedNetPeerAddress = replicatedNetPeer.getAddress();

        for (int i = LENGTH - 1; i >= 0; i--) {

            long thisByte = address[i];
            //from 1##,000,01## to 0000,01##,1## form
            if (thisByte < 0) thisByte = Byte.MAX_VALUE + Math.abs(thisByte);

            long otherByte = replicatedNetPeerAddress[i];
            //from 1##,000,01## to 0000,01##,1## form
            if (otherByte < 0) otherByte = Byte.MAX_VALUE + Math.abs(otherByte);

            if (thisByte > otherByte)
                return 1;

            else if (thisByte < otherByte)
                return -1;
        }
        return 0;
    }

    /**
     * return the XOR metric distance
     *
     * @param firstSequence
     * @param secondSequence
     * @return XOR metric distance
     */
    public byte[] xorDistance(@NonNull byte[] firstSequence, @NonNull byte[] secondSequence) {
        if (firstSequence.length != secondSequence.length)
            return null;

        byte[] distance = new byte[firstSequence.length];

        for (int i = 0; i < distance.length; i++)
            distance[i] = (byte) (firstSequence[i] ^ secondSequence[i]);

        return distance;
    }


    @Override
    public boolean equals(Object replicatedNetPeer) {
        // If the object is compared with itself then return true
        if (replicatedNetPeer == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(replicatedNetPeer instanceof ReplicatedNetPeer)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        ReplicatedNetPeer castedReplicatedNetPeer = (ReplicatedNetPeer) replicatedNetPeer;

        // Compare the data members and return accordingly
        return this.compareTo(castedReplicatedNetPeer) == 0;
    }
}
