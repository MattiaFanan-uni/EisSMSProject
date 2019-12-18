package com.gruppo3.smslibrary.types;

import com.gruppo3.smslibrary.exceptions.InvalidAddressException;
import com.gruppo3.smslibrary.exceptions.InvalidMessageException;

/**
 * The Message class represents a data-set exchanged between a Peer network.
 *
 * @author Mattia Fanan, Giovanni Barca
 */
public class Message {
    /**
     * Maximum length of the payload. Default is 158.
     */
    public static final int MAX_PAYLOAD_LENGTH = 160 - 2; // Control stamp + '#' header - payload separator

    private static final char CONTROL_STAMP = '&';

    private Peer source;
    private Peer destination;
    private String header;
    private String payload;

    /**
     * @deprecated
     * Initializes a newly created Message object so that has the same parameters as the arguments.<br>
     * Payload is valid if its length is less or equal to <code>MAX_PAYLOAD_LENGTH</code>.
     *
     * @param source      Peer representing the message sender
     * @param destination Peer representing the message recipient
     * @param payload     String containing the data to be sent
     * @throws InvalidMessageException If an invalid Payload is passed
     */
    /*@Deprecated
    public Message(Peer source, Peer destination, String payload) throws InvalidMessageException {
        if (!isValidPayload(payload))
            throw new InvalidMessageException();

        this.source = source;
        this.destination = destination;
        this.payload = payload;
    }*/

    /**
     * Initializes a newly created Message object so that has the same parameters as the arguments.<br>
     * Payload is valid if its length is less or equal to <code>MAX_PAYLOAD_LENGTH</code>.
     *
     * @param source      Peer representing the message sender
     * @param destination Peer representing the message recipient
     * @param header     String containing the header data to be sent
     */
    public Message(Peer source, Peer destination, String header) throws InvalidMessageException {
        this.source = source;
        this.destination = destination;
        this.header = header;
        this.payload = ""; // Set to empty string to avoid "null" strings in messages
    }

    /**
     * Initializes a newly created Message object so that has the same parameters as the arguments.<br>
     * Payload is valid if its length is less or equal to <code>MAX_PAYLOAD_LENGTH</code>.
     *
     * @param source      Peer representing the message sender
     * @param destination Peer representing the message recipient
     * @param header     String containing the header data to be sent
     * @param payload     String containing the data to be sent
     * @throws InvalidMessageException If an invalid Payload is passed
     */
    public Message(Peer source, Peer destination, String header, String payload) throws InvalidMessageException {
        if (!isValidPayload(payload))
            throw new InvalidMessageException();

        this.source = source;
        this.destination = destination;
        this.header = header;
        this.payload = payload;
    }

    /**
     * @deprecated
     * Parse a system SmsMessage to custom Message. A message is valid if the first char of the body is the <code>CONTROL_STAMP</code>.
     *
     * @param sourceAddress String containing the source address
     * @param body          String containing the SmsMessage body text
     * @return Message parsed from system SmsMessage
     * @throws InvalidAddressException If an invalid source address is given
     * @throws InvalidMessageException If an invalid message is given
     */
    @Deprecated
    public static Message buildFromSDU(String sourceAddress, String body) throws InvalidAddressException, InvalidMessageException {
        Peer source = new Peer(sourceAddress);
        String header = body.substring(0, body.indexOf('#'));

        if (header.charAt(0) != CONTROL_STAMP)
            throw new InvalidMessageException();

        header = header.substring(1);
        String payload = body.substring(body.indexOf('#') + 1);

        return new Message(source, null, header, payload);
    }

    /**
     * @return A String with the header and the payload
     * @deprecated Gets a String containing the message header joined with the message payload.
     */
    @Deprecated
    public String getSDU() {
        return CONTROL_STAMP + getHeader() + "#" + getPayload();
    }

    /**
     * Gets the source Peer.
     *
     * @return The source peer
     */
    public Peer getSource() {
        return source;
    }

    /**
     * Gets the destination peer.
     *
     * @return The destination peer
     */
    public Peer getDestination() {
        return destination;
    }

    /**
     * Gets a String containing the message header
     *
     * @return A String containing the message header
     */
    public String getHeader() {
        return header;
    }

    /**
     * Gets a String containing the message payload
     *
     * @return A String containing the message payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Returns a String containing the message properties.
     *
     * @return A String containing the message properties
     */
    @Override
    public String toString() {
        String stringRep = "Message:";
        stringRep += " ---Header:" + header;
        stringRep += " ---Payload:" + payload;
        stringRep += " ---Destination:" + destination.getPhoneNumber();
        stringRep += " ---Destination ID:" + destination.getNodeId();
        stringRep += " ---Source:" + source.getPhoneNumber();
        stringRep += " ---Source ID:" + destination.getNodeId();

        return stringRep;
    }

    /**
     * Check if the passed payload is valid.
     *
     * @param payload Payload to be checked
     * @return <code>True</code> if payload is valid, <code>false</code> otherwise
     */
    private boolean isValidPayload(String payload) {
        return payload.length() <= MAX_PAYLOAD_LENGTH;
    }
}
