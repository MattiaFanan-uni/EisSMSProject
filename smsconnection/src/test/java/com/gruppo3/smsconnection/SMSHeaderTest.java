package com.gruppo3.smsconnection;

import com.gruppo3.smsconnection.connection.exception.InvalidHeaderException;
import com.gruppo3.smsconnection.smsdatalink.SMSProtocolControlInformation;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;

import org.junit.Assert;
import org.junit.Test;

public class SMSHeaderTest {
 /*   @Test
    public void stampTest()
    {
        SMSProtocolControlInformation header=null;
        try{
            header= new SMSProtocolControlInformation(new SMSPeer("12345678"),null);
        }
        catch (Exception e){}
        if(header.getStamp().compareTo(Character.toString((char)0x03A6))!=0)
            Assert.fail("not the right stamp");
    }

    @Test
    public void setUpBothPeers()
    {
        SMSProtocolControlInformation header=null;
        SMSPeer destination=null;
        SMSPeer source=null;
        try{
            destination=new SMSPeer("123456");
            source=new SMSPeer("987654");
        }
        catch ( Exception e){}
        try{
            header=new SMSProtocolControlInformation(destination,source);
        }
        catch (InvalidHeaderException e){Assert.fail("shouldn't throw InvalidHeaderException");}
        if(header==null)
            Assert.fail("shouldn't be null");
        if(!header.getSourcePeer().equals(source))
            Assert.fail("source peers should be the same");
        if(!header.getDestinationPeer().equals(destination))
            Assert.fail("destination peers should be the same");
    }

    @Test
    public void setUpSource()
    {
        SMSProtocolControlInformation header=null;
        SMSPeer destination=null;
        SMSPeer source=null;
        try{
            source=new SMSPeer("987654");
        }
        catch ( Exception e){}
        try{
            header=new SMSProtocolControlInformation(destination,source);
        }
        catch (InvalidHeaderException e){Assert.fail("shouldn't throw InvalidHeaderException");}
        if(header==null)
            Assert.fail("shouldn't be null");
        if(!header.getSourcePeer().equals(source))
            Assert.fail("source peers should be the same");
        if(header.getDestinationPeer()!=null)
            Assert.fail("destination peer should be null");
    }

    @Test
    public void setUpDestination()
    {
        SMSProtocolControlInformation header=null;
        SMSPeer destination=null;
        SMSPeer source=null;
        try{
            destination=new SMSPeer("987654");
        }
        catch ( Exception e){}
        try{
            header=new SMSProtocolControlInformation(destination,source);
        }
        catch (InvalidHeaderException e){Assert.fail("shouldn't throw InvalidHeaderException");}
        if(header==null)
            Assert.fail("shouldn't be null");
        if(!header.getDestinationPeer().equals(destination))
            Assert.fail("destination peers should be the same");
        if(header.getSourcePeer()!=null)
            Assert.fail("source peer should be null");
    }

    @Test
    public void setUpBothNull()
    {
        SMSProtocolControlInformation header=null;
        SMSPeer destination=null;
        SMSPeer source=null;
        try{
            header=new SMSProtocolControlInformation(destination,source);
            Assert.fail("should throw InvalidHeaderException");
        }
        catch (InvalidHeaderException e){}//correct
        catch (Exception e){Assert.fail("shouldn't throw this exception");}

    }

    @Test
    public void setDestinationPeerTest()
    {
        SMSProtocolControlInformation header=null;
        SMSPeer destination=null;
        SMSPeer newDestination=null;
        SMSPeer source=null;
        try{
            destination=new SMSPeer("123456");
            newDestination=new SMSPeer("98762345");
            source=new SMSPeer("987654");
        }
        catch ( Exception e){}
        try{
            header=new SMSProtocolControlInformation(destination,source);
        }catch (Exception e){}

        boolean changeResult =header.setDestinationPeer(newDestination);

        if(!changeResult)
            Assert.fail("result should be true");
        //if destination peer is unchanged there are some problems
        if(header.getDestinationPeer().equals(destination))
            Assert.fail("header's destination peer should be changed");
        //if destination peer isn't the new destination peer there are some problems
        if(!header.getDestinationPeer().equals(newDestination))
            Assert.fail("header's destination peer should be the new destination");

        if(!header.getSourcePeer().equals(source))
            Assert.fail("header's source peer should be unchanged");

    }

    @Test
    public void setDestinationPeerBothBecomeNullTest()
    {
        SMSProtocolControlInformation header=null;
        SMSPeer destination=null;
        SMSPeer newDestination=null;
        SMSPeer source=null;
        try{
            destination=new SMSPeer("123456");
        }
        catch ( Exception e){}
        try{
            header=new SMSProtocolControlInformation(destination,source);
        }catch (Exception e){}

        boolean changeResult =header.setDestinationPeer(newDestination);

        //peers can't be both null
        if(changeResult)
            Assert.fail("result should be false");
        if(header.getDestinationPeer()==null)
            Assert.fail("header's destination peer shouldn't be changed");

        //check if source remains unchanged
        if(header.getSourcePeer()!=null)
            Assert.fail("header's source peer shouldn't be unchanged");
    }

    @Test
    public void setDestinationPeerNullTest()
    {
        SMSProtocolControlInformation header=null;
        SMSPeer destination=null;
        SMSPeer newDestination=null;
        SMSPeer source=null;
        try{
            destination=new SMSPeer("123456");
            source=new SMSPeer("987654");
        }
        catch ( Exception e){}
        try{
            header=new SMSProtocolControlInformation(destination,source);
        }catch (Exception e){}

        boolean changeResult =header.setDestinationPeer(newDestination);

        if(!changeResult)
            Assert.fail("result should be true");
        if(header.getDestinationPeer()!=null)
            Assert.fail("header's destination peer should be null");

        //check if source remains unchanged
        if(!header.getSourcePeer().equals(source))
            Assert.fail("header's source peer should be unchanged");

    }

    @Test
    public void setSourcePeerTest()
    {
        SMSProtocolControlInformation header=null;
        SMSPeer destination=null;
        SMSPeer newSource=null;
        SMSPeer source=null;
        try{
            destination=new SMSPeer("123456");
            newSource=new SMSPeer("98762345");
            source=new SMSPeer("987654");
        }
        catch ( Exception e){}
        try{
            header=new SMSProtocolControlInformation(destination,source);
        }catch (Exception e){}

        boolean changeResult =header.setSourcePeer(newSource);

        if(!changeResult)
            Assert.fail("result should be true");
        //if source peer is unchanged there are some problems
        if(header.getSourcePeer().equals(destination))
            Assert.fail("header's source peer should be changed");
        //if source peer isn't the new source peer there are some problems
        if(!header.getSourcePeer().equals(newSource))
            Assert.fail("header's source peer should be the new source");

        if(!header.getDestinationPeer().equals(destination))
            Assert.fail("header's destination peer should be unchanged");

    }

    @Test
    public void setSourcePeerBothBecomeNullTest()
    {
        SMSProtocolControlInformation header=null;
        SMSPeer destination=null;
        SMSPeer newSource=null;
        SMSPeer source=null;
        try{
            source=new SMSPeer("987654");
        }
        catch ( Exception e){}
        try{
            header=new SMSProtocolControlInformation(destination,source);
        }catch (Exception e){}

        boolean changeResult =header.setSourcePeer(newSource);

        //peers can't be both null
        if(changeResult)
            Assert.fail("result should be false");
        if(header.getSourcePeer()==null)
            Assert.fail("header's source peer shouldn't be changed");

        //check if destination remains unchanged
        if(header.getDestinationPeer()!=null)
            Assert.fail("header's destination peer shouldn't be unchanged");
    }

    @Test
    public void setSourcePeerNullTest()
    {
        SMSProtocolControlInformation header=null;
        SMSPeer destination=null;
        SMSPeer newSource=null;
        SMSPeer source=null;
        try{
            destination=new SMSPeer("123456");
            source=new SMSPeer("987654");
        }
        catch ( Exception e){}
        try{
            header=new SMSProtocolControlInformation(destination,source);
        }catch (Exception e){}

        boolean changeResult =header.setSourcePeer(newSource);

        if(!changeResult)
            Assert.fail("result should be true");
        if(header.getSourcePeer()!=null)
            Assert.fail("header's source peer should be null");

        //check if destination remains unchanged
        if(!header.getDestinationPeer().equals(destination))
            Assert.fail("header's destination peer should be unchanged");

    }*/
}
