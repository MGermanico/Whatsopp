/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedClient_1;

import advancedClient.Client_test;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tarde
 */
public class ClientWas extends ClientBase{

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce tu nombre de usuario: ");
        String nombre = sc.nextLine();
        try (Socket socket = new Socket("localhost", 1488);) {
            ClientWas client = new ClientWas(socket, nombre);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    
    public ClientWas(Socket socket, String nombre) {
        super(socket, nombre);
    }

    @Override
    public void listenMessage(Message m) {
        System.out.println("sisi: " + m.toString());
    }
    
    public void sendText(String txt){
        try {
            super.sendMessage(txt);
        } catch (IOException ex) {
            super.closeAll();
        }
    }
    
    public void sendFile(File f){
        super.sendFile("archivo " + f.getAbsolutePath());
    }
    
}
