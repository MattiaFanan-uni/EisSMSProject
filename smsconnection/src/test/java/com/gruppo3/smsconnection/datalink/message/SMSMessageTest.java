package com.gruppo3.smsconnection.datalink.message;

import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.smsdatalink.message.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static com.gruppo3.smsconnection.utils.Utils.getAlphaNumericString;

public class SMSMessageTest {
    SMSPeer validPeer;
    SMSPeer nullPeer=null;
    byte[] validPayolad;


    public SMSMessageTest(){
        try {
            validPayolad = getAlphaNumericString(20).getBytes("UTF-16");
            validPeer=new SMSPeer(SMSPeerTest.validAddress);
        }
        catch (Exception e){}
    }


    @Test
    public void setUpBothPeers()
    {
        SMSMessage message=null;
        try{
            message=new SMSMessage(validPeer,validPeer,validPayolad);
        }
        catch (InvalidPeerException e){Assert.fail("shouldn't throw InvalidPeerException");}
        catch (InvalidPayloadException e){Assert.fail("shouldn't throw InvalidPayloadException");}

        if(message==null)
            Assert.fail("shouldn't be null");
        if(!message.getSourcePeer().equals(validPeer))
            Assert.fail("source peers should be the same");
        if(!message.getDestinationPeer().equals(validPeer))
            Assert.fail("destination peers should be the same");
        if(!Arrays.equals(message.getData(),validPayolad))
            Assert.fail("data should be the same");
    }

    @Test
    public void setUpSource()
    {
        SMSMessage message=null;
        try{
            message=new SMSMessage(nullPeer,validPeer,validPayolad);
        }
        catch (InvalidPeerException e){Assert.fail("shouldn't throw InvalidPeerException");}
        catch (InvalidPayloadException e){Assert.fail("shouldn't throw InvalidPayloadException");}

        if(message==null)
            Assert.fail("shouldn't be null");
        if(!message.getSourcePeer().equals(validPeer))
            Assert.fail("source peers should be the same");
        if(message.getDestinationPeer()!=nullPeer)
            Assert.fail("destination peer should be null");
        if(!Arrays.equals(message.getData(),validPayolad))
            Assert.fail("data should be the same");
    }

    @Test
    public void setUpDestination()
    {
        SMSMessage message=null;
        try{
            message=new SMSMessage(validPeer,nullPeer,validPayolad);
        }
        catch (InvalidPeerException e){Assert.fail("shouldn't throw InvalidPeerException");}
        catch (InvalidPayloadException e){Assert.fail("shouldn't throw InvalidPayloadException");}

        if(message==null)
            Assert.fail("shouldn't be null");
        if(!message.getDestinationPeer().equals(validPeer))
            Assert.fail("source peers should be the same");
        if(message.getSourcePeer()!=nullPeer)
            Assert.fail("destination peer should be null");
        if(!Arrays.equals(message.getData(),validPayolad))
            Assert.fail("data should be the same");
    }

    @Test
    public void setUpBothNull()
    {
        SMSMessage message=null;
        try{
            message=new SMSMessage(nullPeer,nullPeer,validPayolad);
            Assert.fail("should throw InvalidPeerException");
        }
        catch (InvalidPeerException e){}//correct
        catch (InvalidPayloadException e){Assert.fail("shouldn't throw InvalidPayloadException");}
    }

    @Test
    public void buildFromSDU(){
        SMSMessage message=null;
        try{
            message=new SMSMessage(nullPeer,validPeer,validPayolad);
        }
        catch (InvalidPeerException e){Assert.fail("shouldn't throw InvalidPeerException");}
        catch (InvalidPayloadException e){Assert.fail("shouldn't throw InvalidPayloadException");}

        byte[] SDU=message.getSDU();

        try{
            SMSMessage rebuildMessage= SMSMessage.buildFromSDU(validPeer.getAddress(),SDU);

            if (!Arrays.equals(rebuildMessage.getData(),message.getData()))
                Assert.fail("data should be unchanged");
            //null!=null
            if (rebuildMessage.getDestinationPeer()!=message.getDestinationPeer())
                Assert.fail("destination should be unchanged");
            //address!=address
            if (rebuildMessage.getSourcePeer().getAddress().compareTo(message.getSourcePeer().getAddress())!=0)
                Assert.fail("source should be unchanged");
        }
        catch (InvalidPayloadException e){Assert.fail("shouldn't throw InvalidPayloadException");}
        catch (InvalidPeerException e){Assert.fail("shouldn't throw InvalidPeerException");}
        catch (InvalidMessageException e){Assert.fail("shouldn't throw InvalidMessageException");}

    }

    @Test
    public void toStringTestBothPeer() {
        SMSMessage message=null;
        try{
            message=new SMSMessage(validPeer,validPeer,validPayolad);
        }
        catch (InvalidPeerException e){Assert.fail("shouldn't throw InvalidPeerException");}
        catch (InvalidPayloadException e){Assert.fail("shouldn't throw InvalidPayloadException");}

        String expected="Message:";
        try{
            expected=expected+new String(message.getData(),"UTF-16");
        }
        catch (UnsupportedEncodingException e){}
        expected=expected+" ---Destination:" + message.getDestinationPeer().getAddress();
        expected=expected+" ---Source:" + message.getSourcePeer().getAddress();
        Assert.assertEquals(expected , message.toString());
    }

}
