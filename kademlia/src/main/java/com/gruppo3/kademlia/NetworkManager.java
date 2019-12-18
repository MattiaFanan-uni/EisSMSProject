package com.gruppo3.kademlia;

import android.util.Log;

import com.gruppo3.kademlia.types.Bucket;
import com.gruppo3.kademlia.types.RoutingTable;
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

    private String currentNodeId; // currentNodeId is a SHA-1 of the current device phone number in binary base
    private RoutingTable routingTable; // A routing table contains k buckets

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

        // Create a new routing table
        routingTable = new RoutingTable(routingTableSize, bucketSize, currentNodeId);

        // Adds a bootstrap node for testing purposes
        String bootstrapNodePhoneNumber = "+15555215556";
        String bootstrapNodeId = Util.sha1Hash(bootstrapNodePhoneNumber);
        Peer bootstrapNode = new Peer(bootstrapNodePhoneNumber, bootstrapNodeId);
        routingTable.addNode(bootstrapNode);
        Log.d(tag, "bootstrapNodeId: " + bootstrapNodeId);

        // Secondary bootstrap node (for testing purposes)
        String secBootstrapNodePhoneNumber = "0499367669";
        String secBootstrapNodeId = Util.sha1Hash(secBootstrapNodePhoneNumber);
        Peer secBootstrapNode = new Peer(secBootstrapNodePhoneNumber, secBootstrapNodeId);
        routingTable.addNode(secBootstrapNode);
        Log.d(tag, "secBootstrapNodeId: " + secBootstrapNodeId);

        // Printing initial data
        Log.d(tag, "Phone number: " + currentPhoneNumber);
        Log.d(tag, "currentNodeId: " + currentNodeId);
        Log.d(tag, "Routing table size: " + routingTable.getRoutingTableSize());
        Log.d(tag, "Buckets size: " + routingTable.getBucketSize());
        Log.d(tag, "Bucket [0] entries count: " + routingTable.getBucketEntriesCount(0));
        Log.d(tag, "Bucket [1] entries count: " + routingTable.getBucketEntriesCount(1));
        Log.d(tag, "Bucket [2] entries count: " + routingTable.getBucketEntriesCount(2));
        Log.d(tag, "Bucket [3] entries count: " + routingTable.getBucketEntriesCount(3));
    }

    /**
     * Returns the current device ID in the network.
     * @return A string containing an hexadecimal ID in SHA-1 encryption
     */
    public String getCurrentNodeId() {
        return currentNodeId;
    }

    private void nodeLookup(Peer peerToFind) {
        // get the bucket closer to toFind
        // Asycn sends a FIND_NODE request to all entries in the bucket
        // These entries looks in their buckets and returns the bucket closer to toFind
        // Update the result list with received ID, keep the best k nodes
        // Reiterate to these k nodes UNTIL the received IDs are further than the result list
        // When the iteration stops we have the closest nodes to toFind
    }

    private void bootstrapProcess(Peer bootstrapNode) {
        // Add bootstrap node in one of the k-buckets

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
