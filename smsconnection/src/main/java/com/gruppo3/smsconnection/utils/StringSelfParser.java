package com.gruppo3.smsconnection.utils;


import com.gruppo3.smsconnection.replicatednet.dictionary.command.StringParser;

public class StringSelfParser implements StringParser<String> {
    @Override
    public String parseString(String data) {
        return data;
    }

    @Override
    public String parseData(String string) {
        return string;
    }
}
