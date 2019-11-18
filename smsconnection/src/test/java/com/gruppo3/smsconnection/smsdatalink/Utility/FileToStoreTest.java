package com.gruppo3.smsconnection.smsdatalink.Utility;

import android.app.Instrumentation;
import android.content.Context;
import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest({FileInputStream.class})

public class FileToStoreTest{

    Context mock_context = PowerMockito.mock(Context.class);
    File myFile = PowerMockito.mock(File.class);
    FileOutputStream outputStream = PowerMockito.mock(FileOutputStream.class);
    FileInputStream inputStream = PowerMockito.mock(FileInputStream.class);

    //@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    String name = "savedPassword";
    FileToStore ftsObject;

    @Before
    public void initialize() {
        ftsObject = new FileToStore(this.mock_context, this.name);
    }

    String nullString = null;
    String emptyString = "";
    String something = "Something";
    String shouldNotThrowException = "It should not throw an exception";
    String shouldNotBeNull = "It should not throw a null value";


    @Test
    public void writeFileExceptionTest(){
        try{
            ftsObject.writeFile(nullString);
        } catch(Exception e){
            Assert.fail(shouldNotThrowException);
        }
    }

    @Test
    public void readFileTest(){
        String content = ftsObject.readFile();
        if(content == null){ Assert.fail(shouldNotBeNull); }
    }

    @Test
    public void writingAndReadingTest() {
        /*ftsObject.writeFile(something);
        String readResult = ftsObject.readFile();
        Assert.assertEquals(something, readResult);*/
        int mode = Context.MODE_PRIVATE;
        try {
            myFile = new File(this.mock_context.getFilesDir(), this.name);
            outputStream = this.mock_context.openFileOutput(this.name, mode);
            outputStream.write(something.getBytes());
            outputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String text = "";
        try {
            inputStream = this.mock_context.openFileInput(this.name);
            int bufferSize = inputStream.available();
            byte[] buffer = new byte[bufferSize];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(something, text);
    }

    @Test
    public void wipeTest(){
        ftsObject.wipeFile();
        String readResult = ftsObject.readFile();
        Assert.assertEquals(emptyString, readResult);
    }
}