package com.gruppo3.smsconnection.smsdatalink.manager;

/**
 * @author Riccardo Crociani
 */


import com.gruppo3.smsconnection.dictionary.NetDictionary;
import com.gruppo3.smsconnection.connection.exception.InvalidItemException;

import java.util.ArrayList;

public class NetworkDictionary<K,V> implements NetDictionary<K,V> {

    private ArrayList<Pair> elements;

    public NetworkDictionary(){
        elements = new ArrayList<>();
    }

    /**
     * The insertion always works out: if there is alreaday an element with the specified key it is
     * replaced with the new pair. Throws IllegalArgumentException if key is null
     * @param key
     * @param value
     * @throws IllegalArgumentException
     */
    public void add(K key, V value){
        if (key == null)
            throw new IllegalArgumentException();

        remove(key); //Se c'è già rimuovo la coppia..
        elements.add(new Pair(key,value));
    }

    /**
     * Remove the element with the specified key
     * @param key
     */
    public boolean remove (K key){
        return elements.remove(key);
    }

    /**
     * Return the value with specified key, if the key is not found return null
     * @param key
     * @return value
     */
    public V getValue(K key){
        for (int i= 0; i<elements.size(); i++){
            if(elements.get(i).getKey().equals(key))
                return elements.get(i).getValue();
        }
       return null;
    }

    /**
     * Inner class Pair(K, V)
     */
    private class Pair{
        private K key;
        private V value;

        Pair(K key, V value){
            this.key= key;
            this.value=value;
        }
        K getKey(){
            return key;
        }
        V getValue(){
            return value;
        }
    }
}
