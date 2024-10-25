
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author migue
 */
public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String nombre;
    
    public ClientHandler(Socket socket) {
        try{
            this.socket = socket;
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nombre = reader.readLine();
            
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + this.nombre + " se ha unido al chat!");
        } catch (IOException ex) {
            cerrarTodo(socket, reader, writer);
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        String mensajeDelCliente;
        
        while(socket.isConnected()){
            try{
                mensajeDelCliente = reader.readLine();
                broadcastMessage(mensajeDelCliente);
                if (mensajeDelCliente == null){
                    cerrarTodo(socket, reader, writer);
                    break;
                }
            }catch(IOException e){
                cerrarTodo(socket, reader, writer);
                break;
            }
        }
    }
    
    public void broadcastMessage(String mensaje){
        System.out.print("se va a enviar : \"" + mensaje + "\"");
        for (ClientHandler clientHandler : clientHandlers) {
            System.out.println(" de " + clientHandler.nombre);
            try{
                if (!clientHandler.getNombre().equals(this.nombre)) {
                    clientHandler.getWriter().write(mensaje);
                    clientHandler.getWriter().newLine();
                    clientHandler.getWriter().flush();
                }
            }catch(IOException e){
                cerrarTodo(socket, reader, writer);
            }
        }
    }
    
    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + this.nombre + " ha salido del chat.");
    }
    
    public void cerrarTodo(Socket socker, BufferedReader reader, BufferedWriter writer){
        removeClientHandler();
        try{
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public String getNombre() {
        return nombre;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }
    
}
