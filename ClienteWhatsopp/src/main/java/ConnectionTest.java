
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        try(Socket conexionServidor = new Socket("192.168.11.51", 1488);
                DataOutputStream dos = new DataOutputStream(conexionServidor.getOutputStream())){
            System.out.println("Me he conectado al servidor");
            dos.writeUTF("hola costi");
            System.out.println("enviando");
//            DataInputStream dis = new DataInputStream(conexionServidor.getInputStream());
//            String txt = dis.readUTF();
//            System.out.println("ok");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
