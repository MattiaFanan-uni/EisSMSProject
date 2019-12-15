package com.gruppo3.smsconnection.utils;


import com.gruppo3.smsconnection.replicatednet.dictionary.command.StringParser;
/**
 * Utility object used ad identity map in String
 *
 * @author Mattia Fanan
 */
public class StringSelfParser implements StringParser<String> {
    /**
     * Returns the String itself
     *
     * @param data the String to parse from
     * @return the parsed String
     */
    @Override
    public String parseString(String data) {
        return data;
    }

    /**
     * Returns the String itself
     *
     * @param string the string to parse from
     * @return the parsed String
     */
    @Override
    public String parseData(String string) {
        return string;
    }
}
