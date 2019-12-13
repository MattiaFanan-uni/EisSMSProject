package com.gruppo3.smsconnection.utils;

import com.gruppo3.smsconnection.replicatednet.dictionary.command.StringParser;

public class IntegerParser implements StringParser<Integer> {

    @Override
    public String parseString(Integer data) {
        return byteToHex(intToByte(data));
    }

    @Override
    public Integer parseData(String string) {
        return byteToInt(hexToByte(string));
    }

    private String byteToHex(byte[] hashInBytes) {

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }

    private byte[] hexToByte(String hex) {
        byte[] val = new byte[hex.length() / 2];

        for (int i = 0; i < val.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            val[i] = (byte) j;
        }
        return val;
    }

    private byte[] intToByte(int num) {
        return new byte[]{
                (byte) (num >> 24),
                (byte) (num >> 16),
                (byte) (num >> 8),
                (byte) num
        };
    }

    private int byteToInt(byte[] bytes) {

        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF));

    }


}
