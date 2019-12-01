package com.gruppo3.smsconnection.replicatednet.dictionary;

import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.NetDictionary;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * @author Riccardo Crociani
 *
 * ReplicatedNetDictionary contains 2 NetworkDictionary:
 * - genericDictionary<K, V> Risorse-utenti
 * - netDictionary<ReplicatedPeer, SMSPeer> contains the peer allowed to write and read the dictionray
 */

public class ReplicatedNetDictionary<K extends Serializable,V extends Serializable> implements NetDictionary<K,V, ReplicatedNetPeer,SMSPeer> {

    private TreeMap<ReplicatedNetPeer,SMSPeer> peers;
    private HashMap<K,V> resources;
    private int ID;

    public ReplicatedNetDictionary(@NonNull ReplicatedNetPeer netPeer, @NonNull SMSPeer smsPeer) {
        ID= (new Random()).nextInt();
        peers=new TreeMap<>();
        resources=new HashMap<>();
        putPeerIfAbsent(netPeer,smsPeer);
    }

    /**
     * return the dictionary ID
     * @return the dictionary ID
     */
    public int getDictionaryID(){
        return ID;
    }

    /**
     * If the specified key is not already associated with a value associates it with the given value and returns null, else returns the current value.
     * @param resourceKey   key with which the specified value is to be associated
     * @param resourceValue value to be associated with the specified key
     * @return the previous value associated with the specified key, or null if there was no mapping for the key.
     */
    @Override
    public V putResourceIfAbsent(@NonNull K resourceKey, V resourceValue) {
        if(resourceValue==null)
            throw new NullPointerException();
        if(!containsResourceKey(resourceKey))
            return resources.put(resourceKey,resourceValue);
        return getResource(resourceKey);
    }

    /**
     * Removes the resource having this key from this ReplicatedNetDictionary if present.
     *
     * @param resourceKey key of the resource to be removed
     * @return the previous value associated with key, or null if there was no mapping for key.
     */
    @Override
    public V removeResource(@NonNull K resourceKey) {

        return resources.remove(resourceKey);
    }

    /**
     * Returns the resource value to which the specified resource key is mapped, or null if this map contains no mapping for the key
     *
     * @param resourceKey the key whose associated resource value is to be returned
     * @return the resource value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    @Override
    public V getResource(@NonNull K resourceKey) {

        return resources.get(resourceKey);
    }

    /**
     * Returns the number of resources in this dictionary.
     *
     * @return the number of resources in this dictionary.
     */
    @Override
    public int numberOfResources() {
        return resources.size();
    }

    /**
     * Check if the Dictionary contains a resource having the specified key
     *
     * @param resourceKey key of the resource whose presence in this dictionary is to be tested
     * @return <code>true</code> if this dictionary contains a mapping for the specified key
     */
    @Override
    public boolean containsResourceKey(@NonNull K resourceKey) {

        return resources.containsKey(resourceKey);
    }

    /**
     * Check if the Dictionary contains a resource having the specified value
     *
     * @param resourceValue value of the resource whose presence in this dictionary is to be tested
     * @return <code>true</code> if this dictionary contains a mapping for the specified value
     */
    @Override
    public boolean containsResourceValue(V resourceValue) {

        return resources.containsValue(resourceValue);
    }

    /**
     * Return all the Resources in this dictionary
     *
     * @return an array of all resources in this dictionary
     */
    @Override
    public Iterator<Map.Entry<K, V>> getResourcesIterator() {
        return resources.entrySet().iterator();
    }

    /**
     * If the specified key is not already associated with a value associates it with the given value and returns null, else returns the current value.
     * @param peerKey   key with which the specified value is to be associated
     * @param peerValue value to be associated with the specified key
     * @return the previous value associated with the specified key, or null if there was no mapping for the key.
     */
    @Override
    public SMSPeer putPeerIfAbsent (@NonNull ReplicatedNetPeer peerKey, SMSPeer peerValue) {
        if(peerValue==null)
            throw new NullPointerException();
        if(!containsPeerKey(peerKey))
            return peers.put(peerKey,peerValue);
        return getPeer(peerKey);
    }

    /**
     * Removes the peer having this key from this ReplicatedNetDictionary if present.
     *
     * @param peerKey key of the peer to be removed
     * @return the previous value associated with key, or null if there was no mapping for key.
     */
    @Override
    public SMSPeer removePeer(@NonNull ReplicatedNetPeer peerKey) {
        //one peer in necessary
        if(numberOfPeers()<2)
            return null;
        return peers.remove(peerKey);
    }

    /**
     * Returns the peer value to which the specified peer key is mapped, or null if this map contains no mapping for the key
     *
     * @param peerKey the key whose associated peer value is to be returned
     * @return the peer value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    @Override
    public SMSPeer getPeer(@NonNull ReplicatedNetPeer peerKey) {

        return peers.get(peerKey);
    }

    /**
     * Returns the number of peers that can access tis dictionary.
     *
     * @return the number of peers that can access tis dictionary.
     */
    @Override
    public int numberOfPeers() {

        return peers.size();
    }

    /**
     * Check if the Dictionary contains a peer having the specified key
     *
     * @param peerKey key of the peer whose presence in this dictionary is to be tested
     * @return <code>true</code> if this dictionary contains a mapping for the specified key
     */
    @Override
    public boolean containsPeerKey(@NonNull ReplicatedNetPeer peerKey) {

        return peers.containsKey(peerKey);
    }

    /**
     * Check if the Dictionary contains a peer having the specified value
     *
     * @param peerValue value of the peer whose presence in this dictionary is to be tested
     * @return <code>true</code> if this dictionary contains a mapping for the specified value
     */
    @Override
    public boolean containsPeerValue(SMSPeer peerValue) {

        return peers.containsValue(peerValue);
    }

    /**
     * Return all the peer that can access this dictionary in ascending order
     *
     * @return an iterator on all the peer that can access this dictionary
     */
    @Override
    public Iterator<Map.Entry<ReplicatedNetPeer, SMSPeer>> getPeersIteratorAscending() {
        return peers.entrySet().iterator();
    }
}
