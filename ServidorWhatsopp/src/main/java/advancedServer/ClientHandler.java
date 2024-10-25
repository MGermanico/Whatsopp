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
    private OutputStream os = null;
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
            System.out.println("START HANDLER");
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
        System.out.println("RUN()");
        
        byte[] mensajeDelCliente;
        int type;
        while (socket.isConnected()) {
            System.out.println("bucle while SOCKET.ISCONNECTED");
            try(FileOutputStream fos = new FileOutputStream(new File("src/main/java/img/prueba.txt"))){
                System.out.println("leyendo y escribiendo");
                
                byte[] buffer = new byte[1];
                
                is.read(buffer);
                type = buffer[0];
                System.out.println("- - byte de config : " + buffer[0]);
                
                if (type == STRING_TYPE) {
                    System.out.println("ES UN STRING");
                    
                }else if (type == FILE_TYPE) {
                    System.out.println("ES UN FILE");
                    buffer = new byte[4096];

                    int bytesLeidos;
                    while ((bytesLeidos = is.read(buffer)) != -1) {
                        System.out.println("- - lee");
                        os.write(buffer, 0, bytesLeidos);
                    }
                }
                
                System.out.println("FIN");
            } catch (IOException e) {
                System.out.println("ERRORAMEN");
                cerrarTodo();
                break;
            }
        }
    }
    
    public void broadcastMessage(String mensaje) {
//        System.out.print("se va a enviar : \"" + mensaje + "\"");
//        for (ClientHandler clientHandler : clientHandlers) {
//            System.out.println(" de " + clientHandler.nombre);
//            try {
//                if (!clientHandler.getNombre().equals(this.nombre)) {
//                    clientHandler.getWriter().write(mensaje);
//                    clientHandler.getWriter().newLine();
//                    clientHandler.getWriter().flush();
//                }
//            } catch (IOException e) {
//                cerrarTodo(socket, reader, writer);
//            }
//        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + this.nombre + " ha salido del chat.");
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
