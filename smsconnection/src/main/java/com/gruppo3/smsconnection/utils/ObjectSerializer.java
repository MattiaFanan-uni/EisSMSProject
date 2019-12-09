package com.gruppo3.smsconnection.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectSerializer {

    public static <T extends Serializable> byte[] getSerializedBytes(T toSerialize) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(toSerialize);
            out.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            return null;
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }

    public static <T extends Serializable> T getDeserializedObject(byte[] toDeserialize) {

        ByteArrayInputStream bis = new ByteArrayInputStream(toDeserialize);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return (T) o;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }
}
