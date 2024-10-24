
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tarde
 */
public class ConnectionTest {
    public static void main(String[] args) {
        DataInputStream dip;
        try(ServerSocket conexionServidor = new ServerSocket(1488)){
            Socket socketCliente = conexionServidor.accept();
            System.out.println("Cliente conectado al servidor");
            dip = new DataInputStream(socketCliente.getInputStream());
            String text = dip.readUTF();
            System.out.println(text);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
