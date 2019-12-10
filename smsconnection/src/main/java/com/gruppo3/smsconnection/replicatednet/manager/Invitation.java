package com.gruppo3.smsconnection.replicatednet.manager;


import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;

public class Invitation implements Serializable {

    private static char controlCode = '@';
    private long code;
    private boolean accepted;

    public Invitation() {
        code = (new Random()).nextInt();
        accepted = false;
    }

    private Invitation(long code, boolean accepted) {
        this.code = code;
        this.accepted = accepted;
    }

    public void accept() {
        accepted = true;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public long getCode() {
        return code;
    }

    public String getStringInvitation(){
        return controlCode + (accepted ? "S" : "N") + code;
    }

    public static Invitation getFromString(String str) {
        try {
            if (str.charAt(0) != controlCode)
                return null;
            boolean accepted;
            //TODO boolean default value is false
            if(str.charAt(1)=='S')
                accepted=true;
            else if(str.charAt(1)=='N')
                accepted=false;
            else return null;

            return new Invitation(Long.parseLong(str.substring(2)),accepted);
        } catch (Exception e) {
            return null;
        }
    }
}