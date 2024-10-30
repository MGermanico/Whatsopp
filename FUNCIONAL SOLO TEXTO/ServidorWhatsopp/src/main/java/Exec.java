
import java.io.IOException;
import java.net.ServerSocket;
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
public class Exec {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1488);){
            Servidor server = new Servidor(serverSocket);
            server.startServer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
