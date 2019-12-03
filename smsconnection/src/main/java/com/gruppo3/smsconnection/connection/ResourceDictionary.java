package com.gruppo3.smsconnection.connection;

import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.replicatednet.dictionary.command.AddResourceNetCommand;
import com.gruppo3.smsconnection.replicatednet.dictionary.command.RemoveResourceNetCommand;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public interface ResourceDictionary<K extends Serializable,V extends Serializable> extends Serializable {

    /**
     * If the specified key is not already associated with a value associates it with the given value and returns null, else returns the current value.
     *
     * @param resourceKey   key with which the specified value is to be associated
     * @param resourceValue value to be associated with the specified key
     * @return the previous value associated with the specified key, or null if there was no mapping for the key.
     */
    V putResourceIfAbsent (@NonNull K resourceKey, V resourceValue);

    /**
     * Removes the resource having this key from this ReplicatedNetDictionary if present.
     *
     * @param resourceKey key of the resource to be removed
     * @return the previous value associated with key, or null if there was no mapping for key.
     */
    V removeResource(@NonNull K resourceKey);

    /**
     *Returns the resource value to which the specified resource key is mapped, or null if this map contains no mapping for the key
     *
     * @param resourceKey the key whose associated resource value is to be returned
     * @return the resource value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    V getResource(@NonNull K resourceKey);

    /**
     * Returns the number of resources in this dictionary.
     *
     * @return the number of resources in this dictionary.
     */
    int numberOfResources();

    /**
     * Check if the Dictionary contains a resource having the specified key
     *
     * @param resourceKey key of the resource whose presence in this dictionary is to be tested
     * @return <code>true</code> if this dictionary contains a mapping for the specified key
     */
    boolean containsResourceKey(@NonNull K resourceKey);

    /**
     * Check if the Dictionary contains a resource having the specified value
     *
     * @param resourceValue value of the resource whose presence in this dictionary is to be tested
     * @return <code>true</code> if this dictionary contains a mapping for the specified value
     */
    boolean containsResourceValue(V resourceValue);

    /**
     * Return all the Resources in this dictionary
     *
     * @return an iterator on all resources in this dictionary
     */
    Iterator<Map.Entry<K, V>> getResourcesIterator();

}
