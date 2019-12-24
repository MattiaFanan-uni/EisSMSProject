package com.gruppo3.kademlia.types;

import androidx.annotation.NonNull;

import com.gruppo3.smslibrary.types.Peer;
import com.gruppo3.smslibrary.util.Util;

/**
 * Class representing a routing table composed of k-buckets for a kademlia-based network.<br>
 * Note that in this class all node ID must be in binary base.
 *
 * @author Giovanni Barca
 * @version 0.2
 */
public class RoutingTable {
    private int routingTableSize; // Corresponding to the number of bit of a node ID
    private int bucketSize; // k; Maximum number of entries in a bucket
    private String currentNodeId; // Contains the current device node ID for searching purpose

    private Bucket[] routingTable; // The routing table is an array of k-buckets.

    /**
     * Creates a new routing table with <code>routingTableSize</code> buckets of <code>bucketSize</code> length.
     * From the kademlia paper, if node IDs are 160 bit long, there should be 160 buckets.<br>
     * However, for special needs, may be necessary to have less (but more capient) buckets.<br>
     * In every case, nodes IDs must have the same number of bit (or hex, octal, ..) of the routing table size.
     * @param routingTableSize Number of bucket that this routing table contains
     * @param bucketSize Size of each bucket
     * @param currentNodeId NodeId of the current device
     * @throws IllegalArgumentException If currentNodeId length isn't equal to this routing table size
     */
    public RoutingTable(int routingTableSize, int bucketSize, @NonNull String currentNodeId) throws IllegalArgumentException {
        if (currentNodeId.length() != routingTableSize)
            throw new IllegalArgumentException("routingTableSize must be equal to currentNodeId length.");

        this.routingTableSize = routingTableSize;
        this.bucketSize = bucketSize;
        this.currentNodeId = currentNodeId;

        // Initializes every bucket in the routing table
        routingTable = new Bucket[routingTableSize];
        for (int i = 0; i < routingTableSize; i++) {
            routingTable[i] = new Bucket(bucketSize);
        }
    }

    /**
     * Adds node to the proper bucket present in this routing table.
     * @param node Peer object to add to the routing table
     * @return Index of the bucket where the node was put, -1 if the argument node ID is equal to current node ID
     * @throws IllegalArgumentException If argument node ID bits count is not equal to routing table size.
     */
    public int addNode(@NonNull Peer node) throws IllegalArgumentException {
        // Setting node id if it has a valid phone number
        if (node.getNodeId() == null && node.getPhoneNumber() != null)
            node.setNodeId(Util.sha1Hash(node.getPhoneNumber()));

        if (node.getNodeId().length() != routingTableSize)
            throw new IllegalArgumentException("nodeId must be equal to this routing table size.");

        // Searches for the bucket containing nodes with the same first different bit occurrence position of the argument node from the current node
        // e.g. If argument first different bit position (compared to current node) is 5, returns the bucket at position 5 (containing all nodes with same first 4 bits)

        int bucketIndex = compareNodeIds(node);
        if (bucketIndex != -1) // If the given node has not the currentNodeId
            routingTable[bucketIndex].addRecord(node);

        return bucketIndex;
    }

    /**
     * Compares current node ID to argument node ID.<br>
     * Override this method if you need a different bucket fill strategy.
     * @param toCompare Peer object to compare current node ID to
     * @return Position of the first different bit between current node ID and argument node ID, -1 if IDs are equal.
     */
    protected int compareNodeIds(@NonNull Peer toCompare) {
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
        return routingTable[bucketIndex].getEntriesCount();
    }
}
