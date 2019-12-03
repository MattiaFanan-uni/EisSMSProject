package com.gruppo3.smsconnection.replicatednet.manager;

import org.junit.Assert;
import org.junit.Test;

public class InvitationTest {

    @Test
    public void setUp(){
       Invitation inv=new Invitation();

       if(inv.isAccepted())
           Assert.fail("shouldn't starts as accepted");
    }

    @Test
    public void setAccept(){
        Invitation inv=new Invitation();
        inv.accept();
        if(!inv.isAccepted())
            Assert.fail("should be accepted");
    }

}
