/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedClient_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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
    
    private String name;
    private int type = DEFAULT_TYPE;
    private File file = null;
    private String text = null;

    public Message(String text) {
        setText(text);
    }
    
    public Message(File file) {
        setFile(file);
    }
    
    public Message(InputStream is) throws IOException {
        //leer nombre
        byte[] buffer = new byte[1];
        is.read(buffer);
        int length = buffer[0];
//        System.out.println("el nombre mide: " + length);
        buffer = new byte[length];
        is.read(buffer);
        
        String name = "";
        for (int i = 0; i < length; i++) {
            name += (char)buffer[i];
        }
        
        this.name = name;
//        System.out.println("COMPROBACION-------------------  " + this.name);
        
        //leer tipo
        int type;
        buffer = new byte[1];
        is.read(buffer);
        type = buffer[0];

        //leer contenido
        if (type == STRING_TYPE) {
            readingText(is);
        } else if (type == FILE_TYPE) {
            readingFile(is);
        }

    }
    
    private void readingText(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        System.out.println("ES STRING");
        String txt = reader.readLine();
        setText(txt);
    }

    private void readingFile(InputStream is) throws IOException {
        byte[] buffer = new byte[1];
        is.read(buffer);
        int length = buffer[0];
//        System.out.println(length);
        
        buffer = new byte[length];
        is.read(buffer);
        
        String name = "";
        for (int i = 0; i < length; i++) {
            name += (char)buffer[i];
        }
        String filePath = "src/main/java/files/" + name;
        System.out.println(filePath);
//        System.out.println(name);
        try(FileOutputStream fos = new FileOutputStream(new File(filePath))){
//            System.out.println("ES UN FILE");
            buffer = new byte[4096];

            int bytesLeidos;
            while ((bytesLeidos = is.read(buffer)) != -1) {
//                System.out.println("- - lee");
                fos.write(buffer, 0, bytesLeidos);
            }
//            System.out.println("FIN FILE");
            setFile(new File(filePath));
        }catch(Exception ex){
            throw ex;
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
    
    public boolean sendMessage(String nombre, OutputStream out) throws IOException{
//        System.out.println("ENVIAR TIPO: " + type);
        int lengthName = nombre.length();
        out.write(lengthName);
        for (int i = 0; i < lengthName; i++) {
            out.write((byte)nombre.charAt(i));
        }
        out.write(type);
        if (type == STRING_TYPE) {
            sendString(out);
        }else if (type == FILE_TYPE) {
            sendFile(out);
        }
        return true;
    }

    @Override
    public String toString() {
        if (type == STRING_TYPE) {
            return this.name + ": " + this.text;
        }else if(type == FILE_TYPE){
            return "Se ha guardado el archivo \"" + this.file.getName() + "\" en files; enviado por " + this.name;
        }else{
            return null;
        }
    }

    private void sendString(OutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        writer.write(text + "\n");
        writer.flush();
    }

    private void sendFile(OutputStream out) throws IOException {
        String fName = file.getName();
            
        FileInputStream fis = new FileInputStream(file);

        int lengthName = fName.length();
        out.write(lengthName);
        for (int i = 0; i < lengthName; i++) {
            out.write((byte)fName.charAt(i));
        }

        out.flush();
        
        byte[] buffer = new byte[4096];
        int bytesLeidos;

        while ((bytesLeidos = fis.read(buffer)) != -1) {
            out.write(buffer, 0, bytesLeidos);
        }
        
        out.flush();
    }
    
    
}
