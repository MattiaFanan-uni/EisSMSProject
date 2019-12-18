package com.gruppo3.kademlia;

import android.util.Log;

import com.gruppo3.kademlia.types.Bucket;
import com.gruppo3.smslibrary.types.Peer;
import com.gruppo3.smslibrary.util.Util;
import com.gruppo3.smslibrary.SmsManager;
import com.gruppo3.smslibrary.listeners.ReceivedMessageListener;
import com.gruppo3.smslibrary.types.Message;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class NetworkManager {
    enum KademliaCommands {
        INVITE("I"),
        PING("P"),
        FIND_VALUE("V"),
        FIND_NODE("N"),
        STORE("S");

        private String command;

        KademliaCommands(String command) {
            this.command = command;
        }

        public String getCommand() {
            return this.command;
        }
    }

    private final String tag = "kademlia";

    private final int routingTableSize = 160; // Corresponding to the number of bit of a node ID
    private final int bucketSize = 20; // k; Maximum number of entries in a bucket
    private final int concurrentRequests = 3; // α; Maximum number of asynchronous request

    private String currentNodeId; // currentNodeId is a SHA-1 of the current device phone number in hexadecimal
    private ArrayList<Bucket> routingTable = new ArrayList<>(routingTableSize); // The routing table is an ArrayList of k-buckets. Has the same size of the nodes ID cardinality

    /**
     * Initial operations to build or join a Kademlia Network.
     * @param currentPhoneNumber String containing phone number of this device
     * @throws NoSuchAlgorithmException If no Provider supports a MessageDigestSpi implementation for the specified algorithm
     */
    public NetworkManager(String currentPhoneNumber) throws NoSuchAlgorithmException {
        // Setup the listener
        SmsManager.getInstance().addReceivedMessageListener(setUpReceivedMessageListener());

        // Build actual device NodeId
        currentNodeId = Util.sha1Hash(currentPhoneNumber);

        Log.d(tag, "Phone number: " + currentPhoneNumber);
        Log.d(tag, "currentNodeId: " + currentNodeId);

        // Buckets building
        for (int i = 0; i < routingTableSize; i++) {
            routingTable.add(new Bucket(bucketSize));
        }

        // Adds the Bootstrap Node to the Routing Table (for testing purpose)
        String testPhoneNumber4 = "+15555215554";
        routingTable.get(0).addRecord(new Peer(testPhoneNumber4, Util.sha1Hash(testPhoneNumber4)));

        Log.d(tag, "Routing table size: " + routingTable.size());
        Log.d(tag, "Bucket [0] size: " + routingTable.get(0).getBucketSize());
        Log.d(tag, "Bucket [0] entries count: " + routingTable.get(0).getEntriesCount());
    }

    /**
     * Returns the current device ID in the network.
     * @return A string containing an hexadecimal ID in SHA-1 encryption
     */
    public String getCurrentNodeId() {
        return currentNodeId;
    }

    private void nodeLookup(Peer toFind) {
        // get the bucket closer to toFind
        // Asycn sends a FIND_NODE request to all entries in the bucket
        // These entries looks in their buckets and returns the bucket closer to toFind
        // Update the result list with received ID, keep the best k nodes
        // Reiterate to these k nodes UNTIL the received IDs are further than the result list
        // When the iteration stops we have the closest nodes to toFind
    }

    /**
     * Create a ReceivedMessageListener that contains actions to accomplish when receiving a Kademlia command.
     * @return the Kademlia-scope ReceivedMessageListener
     */
    private ReceivedMessageListener setUpReceivedMessageListener() {
        return new ReceivedMessageListener() {
            @Override
            public void onMessageReceived(Message message) {
                String header = message.getHeader();

                if (header == KademliaCommands.INVITE.getCommand()) { // INVITE

                }
                else if (header == KademliaCommands.PING.getCommand()) { // PING

                }
                else if (header == KademliaCommands.FIND_NODE.getCommand()) { // FIND NODE

                }
                else if (header == KademliaCommands.FIND_VALUE.getCommand()) { // FIND VALUE

                }
                else if (header == KademliaCommands.STORE.getCommand()) { // STORE

                }
            }
        };
    }
}
