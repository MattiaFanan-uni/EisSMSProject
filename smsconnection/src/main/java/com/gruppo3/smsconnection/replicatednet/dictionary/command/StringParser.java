package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import java.io.Serializable;

public interface StringParser<T> extends Serializable {

    String parseString(T data);

    T parseData(String string);
}
