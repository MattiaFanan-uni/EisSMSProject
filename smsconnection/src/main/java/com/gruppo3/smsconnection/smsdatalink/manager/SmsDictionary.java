package com.gruppo3.smsconnection.smsdatalink.manager;

/**
 * @author Riccardo Crociani
 */


import com.gruppo3.smsconnection.connection.Dictionary;
import com.gruppo3.smsconnection.connection.exception.InvalidItemException;

import java.util.ArrayList;

public class SmsDictionary<K,V> implements Dictionary<K,V>{

    private ArrayList<Pair> elements;
    private int elementsSize;
    private static final int INITIAL_SIZE = 30;

    public SmsDictionary(){
        elements = new ArrayList<>();
        elementsSize = 0;
    }

    /**
     * The insertion always works out: if there is already an element with the specified key it is
     * replaced with the new pair
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
     * @throws InvalidItemException
     */
    public boolean remove (K key){
        return elements.remove(key);
    }

    /**
     * Return the value with specified key, if the key is not found return null
     * @param key
     * @return value
     * @throws InvalidItemException
     */
    public V find(K key){
        for (int i= 0; i<elementsSize; i++){
            if(elements.get(i).getKey().equals(key))
                return elements.get(i).getValue();
        }
       return null;
    }

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
