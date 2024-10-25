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
        try {
            this.nombre = nombre;
            this.socket = socket;
            this.out = socket.getOutputStream();
            inp = socket.getInputStream();
            listenForMessage();
            enviarMensaje();
        } catch (IOException ex) {
            ex.printStackTrace();
            closeAll();
        }
    }

    public void enviarMensaje() {
        try {

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(nombre + "\n");
            writer.flush();
            System.out.println("NOMBRE ENVIADO " + nombre);

            
        } catch (IOException ex) {
            ex.printStackTrace();
            closeAll();
        }
        try (Scanner scanner = new Scanner(System.in);) {
            
            while (socket.isConnected()) {
                enviarMsg(scanner);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            closeAll();
        }
        
    }

    private void enviarMsg(Scanner scanner) throws IOException {
//        Scanner sc = new Scanner(System.in);
        
        try{
            Message message;
            message = new Message(scanner.nextLine());
            System.out.println("se envia: " + message);
            message.sendMessage(out);
        }catch(java.util.NoSuchElementException ex){
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mensajeDelGrupo;

                while (socket.isConnected()) {
                    try {
                        System.out.println("esperando recibir:");
                        Message m = new Message(inp);
                        System.out.println("algo recibido");
                        if (m.toString() != null) {
                            System.out.println(m.toString());
                        }else{
                            System.out.println("ES NULL");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        closeAll();
                    }
                }
            }
        }).start();
    }

    private void closeAll() {
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
            Client client = new Client(socket, nombre);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
