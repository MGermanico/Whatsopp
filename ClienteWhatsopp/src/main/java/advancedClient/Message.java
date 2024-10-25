/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migue
 */
public class Message {
    
    public static final int DEFAULT_TYPE = 0;
    public static final int STRING_TYPE = 1;
    public static final int FILE_TYPE = 2;
    
    private int type = DEFAULT_TYPE;
    private File file = null;
    private String text = null;

    public Message(String text) {
        setText(text);
    }
    
    public Message(File file) {
        setFile(file);
    }
    
    public Message(InputStream inp) throws IOException {
        byte[] typeBuffer = new byte[1];
        int type = inp.read(typeBuffer);
            
        if (type == STRING_TYPE) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inp));
            this.text += reader.readLine();
        }else if (type == FILE_TYPE) {
//            byte[] buffer = new byte[4096];
//            int bytesLeidos;
//
//            while ((bytesLeidos = inp.read(buffer)) != -1) {
//            fos.write(buffer, 0, bytesLeidos);
        }
    }

    public void setFile(File file) {
        type = FILE_TYPE;
        this.file = file;
    }

    public void setText(String text) {
        type = STRING_TYPE;
        this.text = text;
    }
    
    public boolean sendMessage(OutputStream out) throws IOException{
        out.write(type);
        if (type == STRING_TYPE) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(text);
        }else if (type == FILE_TYPE) {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesLeidos;

            while ((bytesLeidos = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesLeidos);
            }
        }
        return true;
    }
}
