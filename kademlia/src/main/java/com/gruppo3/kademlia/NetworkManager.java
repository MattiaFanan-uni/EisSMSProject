package com.gruppo3.kademlia;

import android.util.Log;

import androidx.annotation.NonNull;

import com.gruppo3.kademlia.types.RoutingTable;
import com.gruppo3.smslibrary.types.Peer;
import com.gruppo3.smslibrary.util.Util;
import com.gruppo3.smslibrary.SmsManager;

/**
 * This class contains all method that accomplish operations for managing a kademlia-type network (joining, searching nodes and resources, etc..).
 *
 * @author Giovanni Barca
 * @version 0.1
 */
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

    private int routingTableSize; // Corresponding to the number of bit of a node ID
    private int bucketSize; // k; Maximum number of entries in a bucket
    private int concurrentRequests; // α; Maximum number of asynchronous request

    private Peer currentPeer; // Peer object representing this device
    private RoutingTable routingTable; // A routing table contains k buckets

    /**
     * Public constructor for joining or building a kademlia-type network with default parameters.
     * @param currentPhoneNumber Sting containing phone number of this device
     */
    public NetworkManager(String currentPhoneNumber) {
        // Assign local variables (parameters taken by kademlia paper suggested values)
        this.routingTableSize = 160; // There are 160 buckets due to 160bit long node IDs (sha-1 generates an 160bit long hash)
        this.bucketSize = 20;
        this.concurrentRequests = 3;

        init(currentPhoneNumber);
    }

    /**
     * Public constructor for joining or building a kademlia-type network with user custom parameters.
     * @param routingTableSize Size of the routing table (how many buckets contains). Usually this value equals node IDs bit count
     * @param bucketSize Size of the buckets. This is a system-wide number and the default value is 20
     * @param concurrentRequests Number of contemporary lookup request a device make to other devices
     * @param currentPhoneNumber String containing phone number of this device
     */
    public NetworkManager(int routingTableSize, int bucketSize, int concurrentRequests, @NonNull String currentPhoneNumber) {
        // Assign local variables
        this.routingTableSize = routingTableSize;
        this.bucketSize = bucketSize;
        this.concurrentRequests = concurrentRequests;

        init(currentPhoneNumber);
    }

    /**
     * Executes initial operations to be capable of creating or joining a kademlia-type network.
     * @param currentPhoneNumber String containing phone number of this device
     */
    private void init(@NonNull String currentPhoneNumber) {
        // Setup the listener
        SmsManager.getInstance().addReceivedMessageListener(setUpReceivedMessageListener());

        // Build actual device NodeId
        String currentNodeId = Util.sha1Hash(currentPhoneNumber);
        currentPeer = new Peer(currentPhoneNumber, currentNodeId);

        // Create a new routing table
        routingTable = new RoutingTable(bucketSize, currentNodeId);

        /* Production stage related operations */
        printDebuggingInfo(currentPeer);
    }

    /**
     * This method, that prints debugging info to the Logcat, can be deleted when production stage of this class is concluded.
     * @param currentPeer Peer object of this device
     */
    private void printDebuggingInfo(Peer currentPeer) {
        // Adds a bootstrap node
        String bootstrapNodePhoneNumber = "+15555215556";
        String bootstrapNodeId = Util.sha1Hash(bootstrapNodePhoneNumber);
        Peer bootstrapNode = new Peer(bootstrapNodePhoneNumber, bootstrapNodeId);
        routingTable.addNode(bootstrapNode);
        Log.d(tag, "bootstrapNodeId: " + bootstrapNodeId);

        // Adding another node to the routing table
        String secondaryNodePhoneNumber = "0499367669";
        String secondaryNodeId = Util.sha1Hash(secondaryNodePhoneNumber);
        Peer secondaryNode = new Peer(secondaryNodePhoneNumber, secondaryNodeId);
        routingTable.addNode(secondaryNode);
        Log.d(tag, "secondaryNodeId: " + secondaryNodeId);

        // Printing initial data
        Log.d(tag, "Phone number: " + currentPeer.getPhoneNumber());
        Log.d(tag, "currentNodeId: " + currentPeer.getNodeId());
        Log.d(tag, "Routing table size: " + routingTableSize);
        Log.d(tag, "Buckets size: " + bucketSize);
        Log.d(tag, "Concurrent requests: " + concurrentRequests);
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
        return currentPeer.getNodeId();
    }

    /**
     * Joins the network of the inviting Peer (bootstrapNode) and executes some initial operations.
     * @param bootstrapNode Inviting peer
     */
    public void joinNetwork(Peer bootstrapNode) {
        // Adding bootstrap node to the routing table
        int bootstrapNodeBucket = routingTable.addNode(bootstrapNode);

        // NODE_LOOKUP of this device node ID (SELF_LOOKUP)
        nodeLookup(currentPeer.getNodeId());

        // Refreshing k-buckets after bootstrapNode bucket with a random lookup request
        String randomNodeId = bootstrapNode.getNodeId().substring(0, bootstrapNodeBucket) + Util.generateRandomNodeID(bootstrapNode.getNodeId().length() - bootstrapNodeBucket); // Getting bootstrap node's base address + Generating random key to attach to baseBootstrapNodeId
        nodeLookup(randomNodeId);
    }
}
