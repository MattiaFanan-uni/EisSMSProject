package com.gruppo3.smslibrary.types;

import androidx.annotation.NonNull;

/**
 * @author Mattia Fanan. Reviewed by Giovanni Barca. Corrected by Giovanni Barca.
 * @version 1
 *
 * The Message class represents a data-set exchanged between a Peer network.
 */
public class Message {
    /**
     * Maximum length of the payload. Default is 158.
     */
    // One sms can be no longer than 160 chars. We use two service chars (CONTROL_STAMP and PAYLOAD_SEPARATOR) so the maximum length can be 160-2 = 158.
    public static final int MAX_PAYLOAD_LENGTH = 160 - 2;

    /**
     * Starting char that every message sent by/to this library has.
     */
    public static final char CONTROL_STAMP = '&';

    /**
     * Char that separates header from payload.
     */
    public static final char PAYLOAD_SEPARATOR = '#';

    private Peer source;
    private Peer destination;
    private String header;
    private String payload;

    /**
     * Initializes a newly created Message object so that has the same parameters as the arguments.<br>
     * Payload is valid if its length is less or equal to <code>MAX_PAYLOAD_LENGTH</code>.
     *
     * @param source Peer representing the message sender
     * @param destination Peer representing the message recipient
     * @param header String containing the header data to be sent
     */
    public Message(Peer source, Peer destination, @NonNull String header) {
        this.source = source;
        this.destination = destination;
        this.header = header;
        this.payload = ""; // Set to empty string to avoid "null" strings in messages
    }

    /**
     * Initializes a newly created Message object so that has the same parameters as the arguments.<br>
     * Payload is valid if its length is less or equal to <code>MAX_PAYLOAD_LENGTH</code>.
     *
     * @param source Peer representing the message sender
     * @param destination Peer representing the message recipient
     * @param header String containing the header data to be sent
     * @param payload String containing the data to be sent
     * @throws IllegalArgumentException If an invalid Payload is passed
     */
    public Message(Peer source, Peer destination, @NonNull String header, @NonNull String payload) throws IllegalArgumentException {
        if (!isValidPayload(payload))
            throw new IllegalArgumentException("Payload can't be longer than MAX_PAYLOAD_LENGTH.");

        this.source = source;
        this.destination = destination;
        this.header = header;
        this.payload = payload;
    }

    /**
     * Parse a system SmsMessage to custom Message.<br>
     * A message is valid if the first char of the body is the <code>CONTROL_STAMP</code>.
     *
     * @param sourcePhoneNumber String containing the source phone number
     * @param body String containing the SmsMessage body text
     * @return Message parsed from system SmsMessage
     * @throws IllegalArgumentException If an invalid message is given
     */
    public static Message buildFromSDU(@NonNull String sourcePhoneNumber, @NonNull String body) throws IllegalArgumentException {
        if (body.charAt(0) != CONTROL_STAMP)
            throw new IllegalArgumentException("First char of the message body isn't equal to the CONTROL_STAMP ('" + CONTROL_STAMP + "')");

        Peer source = new Peer(sourcePhoneNumber);
        String header = body.substring(1, body.indexOf('#'));
        String payload = body.substring(body.indexOf('#') + 1);

        return new Message(source, null, header, payload);
    }

    /**
     * Gets a String containing the message header joined with the message payload.
     *
     * @return A String with the header and the payload
     */
    public String getSDU() {
        return CONTROL_STAMP + getHeader() + PAYLOAD_SEPARATOR + getPayload();
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
     * Gets a String containing the message header.
     *
     * @return A String containing the message header
     */
    public String getHeader() {
        return header;
    }

    /**
     * Gets a String containing the message payload.
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
    private boolean isValidPayload(@NonNull String payload) {
        return payload.length() <= MAX_PAYLOAD_LENGTH;
    }
}
