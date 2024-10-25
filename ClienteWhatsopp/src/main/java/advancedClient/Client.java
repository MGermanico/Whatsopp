/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedClient;

import java.io.BufferedWriter;
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
public class Client {
    private Socket socket = null;

    private String ip = null;
    private String nombre = null;
    private OutputStream out = null;
    private InputStream inp = null;
    
    public Client(Socket socket, String nombre) {
        try{
            this.nombre = nombre;
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
            
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(nombre);
            writer.flush();
            System.out.println("NOMBRE ENVIADO" + nombre);
            Message message;
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
                        Message m = new Message(inp);
                        m.toString();
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
    
     public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce tu nombre de usuario: ");
        String nombre = sc.nextLine();

        try (Socket socket = new Socket("localhost", 1488);) {
            Client client = new Client(socket, nombre);
            client.listenForMessage();
            client.enviarMensaje();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
