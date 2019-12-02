package com.gruppo3.smsconnection.replicatednet.manager;


import java.io.Serializable;
import java.util.Random;

public class Invitation implements Serializable {

    private long code;
    private boolean accepted;

    public Invitation(){
        code=(new Random()).nextLong();
        accepted=false;
    }

    public void accept(){accepted=true;}

    public boolean isAccepted(){return accepted;}

    public long getCode(){return code;}
}
