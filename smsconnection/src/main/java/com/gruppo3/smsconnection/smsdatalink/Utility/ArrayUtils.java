package com.gruppo3.smsconnection.smsdatalink.Utility;

public class ArrayUtils {
    public static <T> boolean addAll(T[] firstArray,T[] secondArray,T[] merged) {

        if(merged.length<firstArray.length+secondArray.length)
            return false;

        //insert first
        for (int i = 0; i < firstArray.length; i++)
            merged[i] = firstArray[i];
        //insert second
        for (int i = 0; i < secondArray.length; i++)
            merged[firstArray.length-1 + i] = secondArray[i];

        return true;
    }
}
