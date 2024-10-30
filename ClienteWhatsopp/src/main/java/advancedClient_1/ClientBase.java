/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedClient_1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migue
 */
public abstract class ClientBase {

    private Socket socket = null;

    private String ip = null;
    private String nombre = null;
    private OutputStream out = null;
    private InputStream inp = null;

    public ClientBase(Socket socket, String nombre) {
        try {
            this.nombre = nombre;
            this.socket = socket;
            this.out = socket.getOutputStream();
            inp = socket.getInputStream();
            listenForMessage();
            sendMessages();
        } catch (IOException ex) {
            ex.printStackTrace();
            closeAll();
        }
    }

    public void sendMessages() {
        sendName();
        sendLoop();
    }
    
    private void sendName(){
        try {

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(nombre + "\n");
            writer.flush();
            System.out.println("NOMBRE ENVIADO " + nombre);

            
        } catch (IOException ex) {
            ex.printStackTrace();
            closeAll();
        }
    }

    private void sendLoop(){
        try (Scanner scanner = new Scanner(System.in);) {
            String txt;
            while (socket.isConnected()) {
                txt = scanner.nextLine();
                sendMessage(txt);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            closeAll();
        }
    }
    
    public void sendMessage(String txt) throws IOException {
        try{
            Message message = null;
            do{
                if (txt.matches("archivo .+\\..+")) {
                    txt = txt.substring(8);
                    message = sendFile(txt);
                }else{
                    message = new Message(txt);
                }
            }while(message == null);
            message.sendMessage(this.nombre, out);
        }catch(java.util.NoSuchElementException ex){
        }
    }
    
    public Message sendFile(String path){
        Message message = null;
        File f;
        f = new File(path);
        if (f.exists()) {
            message = new Message(f);
        }else{
            System.out.println("!!! RUTA INCORRECTA !!!");
        }
        return message;
    }
    
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mensajeDelGrupo;

                while (socket.isConnected()) {
                    try {
//                        System.out.println("esperando recibir:");
                        Message m = new Message(inp);
//                        System.out.println("algo recibido");
                        if (m.toString() != null) {
                            listenMessage(m);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        closeAll();
                    }
                }
            }
        }).start();
    }
    
    public abstract void listenMessage(Message m);

    public void closeAll() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (out != null) {
                out.close();
            }
            if (inp != null) {
                inp.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
