/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migue
 */
public class Client {
    private Socket socket = null;

    private String ip = null;
    private String nombre = null;
    private OutputStream out = null;
    private InputStream inp = null;
    
    public Client(Socket socket) {
        try{
            this.socket = socket;
            out = socket.getOutputStream();
            inp = socket.getInputStream();
            listenForMessage();
            enviarMensaje();
        } catch (IOException ex) {
            closeAll();
        }
    }
    
    public void enviarMensaje(){
        try (Scanner scanner = new Scanner(System.in);){
            System.out.println("\tESCRIBE TU NOMBRE: ");
            //envia el nombre
            Message message = new Message(scanner.nextLine());
            while(socket.isConnected()){
                message = new Message(scanner.nextLine());
                message.sendMessage(out);
            }
        } catch (IOException ex) {
            closeAll();
        }
    }
    
    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mensajeDelGrupo;
                
                while(socket.isConnected()){
                    try{
                        
                    }catch(IOException e){
                        closeAll();
                    }
                }
            }
        }).start();
    }

    private void closeAll() {
        try{
            if (socket != null) {
                socket.close();
            }
            if (out != null) {
                out.close();
            }
            if (inp != null) {
                inp.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    
}
