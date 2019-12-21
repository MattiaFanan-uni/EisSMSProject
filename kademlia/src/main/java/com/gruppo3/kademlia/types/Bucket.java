package com.gruppo3.kademlia.types;

import com.gruppo3.smslibrary.types.Peer;

import java.util.ArrayList;

/**
 * Class representing a k-bucket where k is a system wide number (like 20). A bucket contains up to k entries.<br>
 * A new discovered node is placed on the bottom of the bucket.<br>
 * If the bucket is full, the least recently seen node (last node) in the bucket is PINGed. If it's still alive, the new node is placed in a replacement cache.
 */
public class Bucket {
    private ArrayList<Peer> nodes; // Bucket entries
    private ArrayList<Peer> replacementCache = new ArrayList<Peer>(); // Replacement cache if bucket is full

    private int bucketSize; // Size of the bucket
    private int entriesCount; // Number of "recorded" entries

    /**
     * Instantiate a new bucket with k entries.
     * @param bucketSize Size of the bucket (must be a system wide number)
     * @throws IllegalArgumentException If k is smaller than 1
     */
    public Bucket(int bucketSize) {
        if (bucketSize < 1)
            throw new IllegalArgumentException();

        this.bucketSize = bucketSize;
        this.entriesCount = 0;

        nodes = new ArrayList<>(bucketSize);
        replacementCache = new ArrayList<>();
    }

    /**
     * Place a new peer in the bucket. If the bucket is full and the least recently seen node is still alive then the peer is placed in a replacement cache.
     * @param newPeer Peer to be added to the bucket
     */
    public void addRecord(Peer newPeer) {
        if (entriesCount < bucketSize) {
            nodes.add(newPeer);
            entriesCount++;
        }
        else {
            /*if (ping(lastRecentlySeen))
                replacementCache.add(newPeer);
            else {
                remove(lastRecentlySeen);
                add(newPeer);
            }*/
        }
    }

    /**
     * Returns size of the bucket.
     * @return size of the bucket
     */
    public int getBucketSize() {
        return bucketSize;
    }

    /**
     * Returns number of entries in the bucket
     * @return number of entries in the bucket
     */
    public int getEntriesCount() {
        return entriesCount;
    }
}
