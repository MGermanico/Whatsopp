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
    private String nombre;


    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            socket.setSoTimeout(5000);
            this.nombre = reader.readLine();
            clientHandlers.add(this);
            System.out.println("START HANDLER");
            // broadcastMessage("SERVER: " + this.nombre + " se ha unido al chat!");
        } catch (IOException ex) {
//            cerrarTodo(socket, reader);
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        byte[] mensajeDelCliente;
        OutputStream os = null;
        InputStream is = null;
        while (socket.isConnected()) {
            try{
                is = socket.getInputStream();
                mensajeDelCliente = is.readAllBytes();
                os = socket.getOutputStream();
                os.write(mensajeDelCliente);
            } catch (IOException e) {
//                cerrarTodo(socket, reader);
                break;
            } finally{
                if(os != null){
                    try {
                        os.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(is != null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
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
    public void cerrarTodo(Socket socker, BufferedReader bufferedReader) {
        removeClientHandler();
        try {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
