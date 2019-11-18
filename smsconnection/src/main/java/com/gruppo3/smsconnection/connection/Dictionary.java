package com.gruppo3.smsconnection.connection;

/**
 * @author Riccardo Crociani
 */
public interface Dictionary<K, V> {

    /**
     * Add the element
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
     * Find the element
     * @param key
     * @return value
     */
    V find(K key);

}
