package com.gruppo3.smsconnection.replicatednet.dictionary.command;

public interface StringParser<T> {

    String parseString(T data);

    T parseData(String string);
}
