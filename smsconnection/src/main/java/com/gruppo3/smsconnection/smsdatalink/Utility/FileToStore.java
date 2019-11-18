package com.gruppo3.smsconnection.smsdatalink.Utility;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

/**
 * @author Fabio Marangoni
 * This class handles the text file for the password
 */
public class FileToStore{

    private Context context;
    private String filename;

    public FileToStore(Context context, String filename){
        this.context = context;
        this.filename = filename;
    }


    /**
     * This method writes a string inside the file. It eventually overwrites the precedent content.
     * @param fileContent the string the user wants to write in the file.
     */
    public boolean writeFile(String fileContent) {
        int mode = Context.MODE_PRIVATE;
        try {
            //creation of the file in the app directory.
            File myFile = new File(this.context.getFilesDir(), this.filename);

            FileOutputStream outputStream = this.context.openFileOutput(this.filename, mode);
            outputStream.write(fileContent.getBytes());
            outputStream.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    /**
     * This method shows the content of the file.
     * @return a string representing the content of the file.
     */
    public String readFile(){
        String text = "";
        try {
            FileInputStream inputStream = this.context.openFileInput(this.filename);
            int bufferSize = inputStream.available();
            byte[] buffer = new byte[bufferSize];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return text;
    }

    /**
     * This method deletes the content of the file, leaving it empty.
     */
    public void wipeFile(){
        String emptyString = "";
        this.writeFile(emptyString);
    }

}