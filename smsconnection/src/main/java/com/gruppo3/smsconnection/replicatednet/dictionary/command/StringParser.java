package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import java.io.Serializable;

/**
 * Utility object used to map an object to a String and reverse
 *
 * @param <T> type of object to parse and parse from
 * @author Mattia Fanan
 */
public interface StringParser<T> extends Serializable {

    /**
     * Parses a String from an object
     *
     * @param data the object to parse from
     * @return the parsed String
     */
    String parseString(T data);

    /**
     * Parses an object from the passed String
     *
     * @param string the string to parse from
     * @return the parsed object
     */
    T parseData(String string);
}
