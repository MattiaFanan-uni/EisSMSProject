package com.gruppo3.kademlia.types;

import com.gruppo3.smslibrary.types.Peer;

import java.util.ArrayList;

/**
 * Class representing a k-bucket. k must be a system wide number (like 20). A bucket contains up to k entries.<br>
 * If the bucket is NOT full, a new discovered node is placed after nodes already in the bucket.<br>
 * If the bucket is full, the least recently seen node (last node) in the bucket is PINGed. If it's still alive, the new node is placed in a replacement cache.
 *
 * @author Giovanni Barca
 * @version 0.1
 */
class Bucket {
    private ArrayList<Peer> nodes; // Bucket entries
    private ArrayList<Peer> replacementCache = new ArrayList<Peer>(); // Replacement cache if bucket is full

    private static boolean firstRun = true; // This variable is needed to set buckets size at first instance of this class
    private static int bucketSize; // Size of the bucket
    private int entriesCount; // Number of "recorded" entries

    /**
     * Instantiate a new bucket with k entries.
     * @param bucketSize Size of the bucket (must be a system wide number)
     * @throws IllegalArgumentException If k is smaller than 1 or if new bucket has different size from other buckets.
     */
    Bucket(int bucketSize) {
        if (bucketSize < 1)
            throw new IllegalArgumentException("Bucket size must be greater than 0.");

        if (!firstRun && bucketSize != Bucket.bucketSize)
            throw new IllegalArgumentException("Buckets must be all of the same size.");

        firstRun = false; // Setting this variable to false so the static bucketSize will be no more modified

        Bucket.bucketSize = bucketSize;
        this.entriesCount = 0;

        nodes = new ArrayList<>(bucketSize); // Pre-allocate bucketSize elements on the nodes ArrayList
        replacementCache = new ArrayList<>(); // replacementCache should be indefinitely long
    }

    /**
     * Place a new peer in the bottom of the bucket.<br>
     * If the bucket is full and the least recently seen node is still alive then the peer is placed in a replacement cache.
     * @param newPeer Peer to be added to the bucket
     */
    void addRecord(Peer newPeer) {
        if (entriesCount < bucketSize) {
            nodes.add(newPeer);
            entriesCount++; // Refresh number of entries in this bucket
        }
        else {
            replacementCache.add(newPeer);
        }
    }

    /**
     * Returns size of the bucket.
     * @return size of the bucket
     */
    int getBucketSize() {
        return bucketSize;
    }

    /**
     * Returns number of entries in the bucket
     * @return number of entries in the bucket
     */
    int getEntriesCount() {
        return entriesCount;
    }
}
