/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedClient;

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
public class Client_test {

    private Socket socket = null;

    private String ip = null;
    private String nombre = null;
    private OutputStream out = null;
    private InputStream inp = null;

    public Client_test(Socket socket, String nombre) {
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
                    message = sendFile(txt);
                }else{
                    message = new Message(txt);
                }
            }while(message == null);
            message.sendMessage(this.nombre, out);
        }catch(java.util.NoSuchElementException ex){
        }
    }

    
    
    private Message sendFile(String txt){
        txt = txt.substring(8);
        Message message = null;
        File f;
        f = new File(txt);
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
    
    private void listenMessage(Message m){
        System.out.println(m.toString());
    }

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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce tu nombre de usuario: ");
        String nombre = sc.nextLine();
        try (Socket socket = new Socket("localhost", 1488);) {
            Client_test client = new Client_test(socket, nombre);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
