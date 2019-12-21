package com.gruppo3.kademlia.types;

import android.util.Log;

import com.gruppo3.smslibrary.types.Peer;

import java.util.ArrayList;

/**
 * Class representing a routing table composed of k-buckets.<br>
 * Note that in this class all node ID must be in binary base
 */
public class RoutingTable {
    private int routingTableSize; // Corresponding to the number of bit of a node ID
    private int bucketSize; // k; Maximum number of entries in a bucket
    private String currentNodeId; // Contains the current device node ID for searching purpose

    private ArrayList<Bucket> routingTable; // The routing table is an ArrayList of k-buckets. Has the same size of the nodes ID cardinality

    /**
     * Creates a new routing table with <code>routingTableSize</code> buckets of <code>bucketSize</code> length.
     * @param routingTableSize Number of bucket that this routing table contains
     * @param bucketSize Size of each bucket
     * @param currentNodeId NodeId of the current device
     * @throws IllegalArgumentException If currentNodeId length isn't equal to this routing table size
     */
    public RoutingTable(int routingTableSize, int bucketSize, String currentNodeId) throws IllegalArgumentException {
        if (currentNodeId.length() != routingTableSize) {
            Log.e("routingTableSize", String.valueOf(routingTableSize));
            Log.e("currentNodeId length", String.valueOf(currentNodeId.length()));
            throw new IllegalArgumentException("routingTableSize must be equal to currentNodeId bits count!");
        }

        this.routingTableSize = routingTableSize;
        this.bucketSize = bucketSize;
        this.currentNodeId = currentNodeId;

        routingTable = new ArrayList<>(routingTableSize);
        for (int i = 0; i < routingTableSize; i++) {
            routingTable.add(new Bucket(bucketSize));
        }
    }

    /**
     * Adds node to the right bucket present in this routing table.
     * @param node Peer object to add to the routing table
     * @return Index of the bucket where the node was put, -1 if the argument node ID is equal to current node ID
     * @throws IllegalArgumentException If argument node ID is not of the same length of current node ID
     */
    public int addNode(Peer node) throws IllegalArgumentException {
        if (currentNodeId.length() != node.getNodeId().length()) // Nodes ID must be of the same length (usually 40 chars)
            throw new IllegalArgumentException("Argument nodeId must be equal to the current node ID bits count!");

        // Search for the bucket containing nodes with the same first different bit occurrence position of the argument node from the current node
        // e.g. If argument first different bit position (compared to current node) is 5, returns the 5th bucket (containing all nodes with same first 4 bits)

        int bucketIndex = compareNodeIds(node);
        if (bucketIndex != -1) // If the given node has not the currentNodeId
            routingTable.get(bucketIndex).addRecord(node);
        else {
            // Do something
        }

        return bucketIndex;
    }

    /**
     * Compares current node ID to argument node ID.
     * @param toCompare Peer object to compare current node ID to
     * @return Position of the first different bit between current node ID and argument node ID, -1 if IDs are equal.
     */
    private int compareNodeIds(Peer toCompare) {
        for (int i = 0; i < currentNodeId.length(); i++) {
            if (currentNodeId.charAt(i) != toCompare.getNodeId().charAt(i))
                return i;
        }

        return -1;
    }

    /**
     * Returns size of the routing table (how many buckets contains).
     * @return How many buckets this routing table contains
     */
    public int getRoutingTableSize() {
        return routingTableSize;
    }

    /**
     * Returns size of each bucket
     * @return Size of each bucket
     */
    public int getBucketSize() {
        return bucketSize;
    }

    /**
     * Returns how many entries does a bucket have.
     * @param bucketIndex Index of the bucket of which to know the number of entries
     * @return Number of entries that the argument bucket has
     */
    public int getBucketEntriesCount(int bucketIndex) {
        return routingTable.get(bucketIndex).getEntriesCount();
    }
}
