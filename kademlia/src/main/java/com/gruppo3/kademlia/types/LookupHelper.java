package com.gruppo3.kademlia.types;

import android.util.Pair;

import com.gruppo3.smslibrary.types.Peer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class LookupHelper {
    Peer currentPeer;//peer to search
    BitSet currentBitSet;// Bitset to search
    TreeSet<Pair<BitSet,Peer>> currentKClosest;
    int k;//max size of the currentKClosest list
    HashSet<Peer> toCommitPeers;//peers

    public LookupHelper(int k, Peer currentPeer) {
        this.k = k;
        currentKClosest = new TreeSet<>();
        toCommitPeers = new HashSet<>();
        currentBitSet=new BitSet(160);
        currentBitSet.set(currentPeer.getNodeId());
        this.currentPeer=currentPeer;
    }

    public void insert(Peer toInsert) {
        toCommitPeers.add(toInsert);
    }

    public TreeSet<Peer> commit() {
        TreeSet<Pair<BitSet,Peer>> updateKClosest=(TreeSet<Pair<BitSet,Peer>>)currentKClosest.clone();

        Iterator<Peer> iterator = toCommitPeers.iterator();

        while (iterator.hasNext()) {
            Peer iteratorPeer=iterator.next();
            BitSet iteratorBitSet=new BitSet(160);
            iteratorBitSet.set(iteratorPeer.getNodeId());
            iteratorBitSet.xor(currentBitSet);
            updateKClosest.add(new Pair<BitSet,Peer>(iteratorBitSet,iteratorPeer));
            //TODO we need something that works whit xor distance order
            //maintains the size of K
            if(updateKClosest.size()>k)
                updateKClosest.remove(updateKClosest.last());
        }
        //now we have the K closest node
        //return the difference because we need to send a FIND to them

        TreeSet<Peer> toReturn=(TreeSet<Peer>)updateKClosest.clone();
        toReturn.removeAll(currentKClosest);

        //update currentKClosest
        currentKClosest=updateKClosest;

        return toReturn;
    }
}
