package com.gruppo3.smsconnection.connection;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Riccardo Crociani
 * Generic dictionary interface for net resource sharing
 */
public interface NetDictionary<RK extends Serializable, RV extends Serializable, PK extends Serializable, PV extends Serializable> extends Serializable {


    /**
     * If the specified key is not already associated with a value associates it with the given value and returns null, else returns the current value.
     * @param resourceKey   key with which the specified value is to be associated
     * @param resourceValue value to be associated with the specified key
     * @return the previous value associated with the specified key, or null if there was no mapping for the key.
     */
    RV putResourceIfAbsent (@NonNull RK resourceKey, RV resourceValue);

    /**
     * Removes the resource having this key from this NetDictionary if present.
     * @param resourceKey key of the resource to be removed
     * @return the previous value associated with key, or null if there was no mapping for key.
     */
    RV removeResource(@NonNull RK resourceKey);

    /**
     *Returns the resource value to which the specified resource key is mapped, or null if this map contains no mapping for the key
     * @param resourceKey the key whose associated resource value is to be returned
     * @return the resource value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    RV getResource(@NonNull RK resourceKey);

    /**
     * Returns the number of resources in this dictionary.
     * @return the number of resources in this dictionary.
     */
    int numberOfResources();

    /**
     * Check if the Dictionary contains a resource having the specified key
     * @param resourceKey key of the resource whose presence in this dictionary is to be tested
     * @return <code>true</code> if this dictionary contains a mapping for the specified key
     */
    boolean containsResourceKey(@NonNull RK resourceKey);

    /**
     * Check if the Dictionary contains a resource having the specified value
     * @param resourceValue value of the resource whose presence in this dictionary is to be tested
     * @return <code>true</code> if this dictionary contains a mapping for the specified value
     */
    boolean containsResourceValue(RV resourceValue);

    /**
     * Return all the Resources in this dictionary
     * @return an iterator on all resources in this dictionary
     */
    Iterator<Map.Entry<RK, RV>> getResourcesIterator();



    /**
     * If the specified key is not already associated with a value associates it with the given value and returns null, else returns the current value.
     * @param peerKey   key with which the specified value is to be associated
     * @param peerValue value to be associated with the specified key
     * @return the previous value associated with the specified key, or null if there was no mapping for the key.
     */
    PV putPeerIfAbsent (@NonNull PK peerKey, PV peerValue);

    /**
     * Removes the peer having this key from this NetDictionary if present.
     * @param peerKey key of the peer to be removed
     * @return the previous value associated with key, or null if there was no mapping for key.
     */
    PV removePeer(@NonNull PK peerKey);

    /**
     *Returns the peer value to which the specified peer key is mapped, or null if this map contains no mapping for the key
     * @param peerKey the key whose associated peer value is to be returned
     * @return the peer value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    PV getPeer(@NonNull PK peerKey);

    /**
     * Returns the number of peers that can access tis dictionary.
     * @return the number of peers that can access tis dictionary.
     */
    int numberOfPeers();

    /**
     * Check if the Dictionary contains a peer having the specified key
     * @param peerKey key of the peer whose presence in this dictionary is to be tested
     * @return <code>true</code> if this dictionary contains a mapping for the specified key
     */
    boolean containsPeerKey(@NonNull PK peerKey);

    /**
     * Check if the Dictionary contains a peer having the specified value
     * @param peerValue value of the peer whose presence in this dictionary is to be tested
     * @return <code>true</code> if this dictionary contains a mapping for the specified value
     */
    boolean containsPeerValue(PV peerValue);

    /**
     * Return all the peer that can access this dictionary in acsending order
     * @return an iterator on all the peer that can access this dictionary
     */
    Iterator<Map.Entry<PK, PV>> getPeersIteratorAscending();
}
