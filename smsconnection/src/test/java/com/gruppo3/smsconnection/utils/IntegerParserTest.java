package com.gruppo3.smsconnection.utils;

import org.junit.Assert;
import org.junit.Test;

public class IntegerParserTest {

    @Test
    public void toStringAndBackPos() {
        Integer num = 6;

        String a = new IntegerParser().parseString(num);

        Integer numB = new IntegerParser().parseData(a);

        Assert.assertEquals(num, numB);

    }

    @Test
    public void toStringAndBackNeg() {
        Integer num = -4566;

        String a = new IntegerParser().parseString(num);

        Integer numB = new IntegerParser().parseData(a);

        Assert.assertEquals(num, numB);

    }
}
