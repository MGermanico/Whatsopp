/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedServer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migue
 */
class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader reader;
    public OutputStream os = null;
    private InputStream is = null;
    private String nombre;


    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            is = socket.getInputStream();
            os = socket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nombre = reader.readLine();
            clientHandlers.add(this);
            System.out.println("START HANDLER de: " + this.nombre);
            // broadcastMessage("SERVER: " + this.nombre + " se ha unido al chat!");
        } catch (IOException ex) {
            cerrarTodo();
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static final int DEFAULT_TYPE = 0;
    public static final int STRING_TYPE = 1;
    public static final int FILE_TYPE = 2;
    
    @Override
    public void run() {
        try {
            byte[] buffer = new byte[4096];
            int bytesLeidos;
            String txt;
            while ((bytesLeidos = is.read(buffer)) != -1) {
//                System.out.println("- - lee");
                for (ClientHandler clientHandler : clientHandlers) {
                    txt = "";
                    if (clientHandler != this) {
                        for (int i = 0; i < buffer.length; i++) {
                            txt += (char)buffer[i];
                        }
                        System.out.println(txt);
                        clientHandler.os.write(buffer, 0, bytesLeidos);
                    }
                    clientHandler.os.flush();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void readingText() throws IOException {
        System.out.println("ES STRING");
        String txt = reader.readLine();
        System.out.println(txt);
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
    }
    public void cerrarTodo() {
        removeClientHandler();
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(is != null){
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(os != null){
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(reader != null){
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    

    
}
