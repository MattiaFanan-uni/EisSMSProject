package com.gruppo3.smsconnection.smsdatalink.manager;

/**
 * @author Riccardo Crociani
 * Revisione di Luca Crema e Enrico Cestaro
 *
 * Dictionary tests
 */

import com.gruppo3.smsconnection.connection.exception.InvalidItemException;

import org.junit.Assert;
import org.junit.Test;

public class SmsDictionaryTest {

    @Test
    public void add() {
        SmsDictionary dict = new SmsDictionary();
        dict.add("key", "value");
        Assert.assertEquals("value", dict.find("key"));
    }

    @Test
    public void addElementAlreadyIn(){
        SmsDictionary dict = new SmsDictionary();
        dict.add("key", "value");
        dict.add("key", "test1");
        Assert.assertEquals("test1", dict.find("key"));
    }

    @Test
    public void addNullKey(){
        SmsDictionary dict = new SmsDictionary();
        try {
            dict.add(null, "value");
        }
        catch(InvalidItemException e){}
        Assert.fail("Should have thrown InvalidItemException");
    }

    @Test
    public void removeANonExistingElement(){
        SmsDictionary dict = new SmsDictionary();
        Assert.assertEquals(false, dict.remove("key"));
    }

    @Test
    public void remove() {
        SmsDictionary dict = new SmsDictionary();
        dict.add("key", "value");
        Assert.assertEquals(true, dict.remove("key"));
    }

    @Test
    public void find() {
        SmsDictionary dict = new SmsDictionary();
        dict.add("key", "value");
        Assert.assertEquals("value", dict.find("key"));
    }

    @Test
    public void findANonExistingElement(){
        SmsDictionary dict = new SmsDictionary();
        dict.add("key", "value");
        Assert.assertEquals(null, dict.find("keyTest1"));
    }
}