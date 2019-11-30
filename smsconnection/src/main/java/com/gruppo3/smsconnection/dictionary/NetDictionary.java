package com.gruppo3.smsconnection.dictionary;

/**
 * @author Riccardo Crociani
 * Generic dictionary interface
 */
public interface NetDictionary<K, V> {

    /**
     * Add the element in the first available position
     * @param key
     * @param value
     */
    void add(K key, V value);

    /**
     * Remove the element
     * @param key
     * @return boolean
     */
    boolean remove(K key);

    /**
     * Find the element with the specified key and return the value
     * @param key
     * @return value
     */
    V getValue(K key);

}
