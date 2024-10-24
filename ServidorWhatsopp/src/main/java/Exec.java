
import java.io.DataOutputStream;
import java.net.Socket;

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
        try(Socket conexionServidor = new Socket("localhost", 1488);
                DataOutputStream dos = new DataOutputStream(conexionServidor.getOutputStream())){
            System.out.println("Me he conectado al servidor");
            dos.writeUTF("Mensaje de prueba");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
