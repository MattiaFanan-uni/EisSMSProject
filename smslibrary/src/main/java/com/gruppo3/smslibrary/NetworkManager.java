package com.gruppo3.smslibrary;

import android.provider.Telephony;

import com.gruppo3.smslibrary.listeners.ReceivedMessageListener;
import com.gruppo3.smslibrary.types.Message;
import com.gruppo3.smslibrary.types.Peer;
import com.gruppo3.smslibrary.util.Util;

import java.security.NoSuchAlgorithmException;

public class NetworkManager {
    /*
        INVITE          I
        PING            P
        FIND_VALUE      V
        FIND_NODE       N
        STORE           S
    */

    enum KademliaCommands {
        INVITE('I'),
        PING('P'),
        FIND_VALUE('V'),
        FIND_NODE('N'),
        STORE('S');

        private char command;

        KademliaCommands(char command) {
            this.command = command;
        }

        public char getCommand() {
            return this.command;
        }
    }

    // Buckets size
    private final int BUCKET_SIZE = 3;

    // Device NodeId
    private String currentNodeId;

    /**
     * Initial operations to build or join a Kademlia Network.
     * @param phoneNumer String containing phone number of this device
     * @throws NoSuchAlgorithmException If no Provider supports a MessageDigestSpi implementation for the specified algorithm
     */
    public NetworkManager(String phoneNumer) throws NoSuchAlgorithmException {
        // Setup the listener
        SmsManager.getInstance().addReceivedMessageListener(setUpReceivedMessageListener());

        // Build actual device NodeId
        currentNodeId = Util.sha1Hash(phoneNumer);
    }

    /**
     * Create a ReceivedMessageListener that contains actions to accomplish when receiving a Kademlia command.
     * @return the Kademlia-scope ReceivedMessageListener
     */
    private ReceivedMessageListener setUpReceivedMessageListener() {
        return new ReceivedMessageListener<Message>() {
            @Override
            public void onMessageReceived(Message message) {
                char header = message.getHeader();

                if (header == KademliaCommands.INVITE.getCommand()) // Received invitation
                    joinNetwork(message.getSource());
            }
        };
    }

    /**
     * Sends an invite sms to the peer argument.
     * @param toInvite Peer to be invited
     */
    public void inviteDevice(Peer toInvite) {
        Message inviteMessage = new Message(null, toInvite, KademliaCommands.INVITE.getCommand());
        SmsManager.getInstance().sendMessage(inviteMessage);
    }

    /**
     * Receives an invite sms to join a network
     * @param inviteSource Peer that sent the invitation
     */
    private void joinNetwork(Peer inviteSource) {
        // Lookup request to the inviting peer (Bootstrap node)
        Message lookupRequestMessage = new Message(null, inviteSource, KademliaCommands.FIND_NODE.getCommand(), currentNodeId);
        SmsManager.getInstance().sendMessage(lookupRequestMessage);
    }
}
