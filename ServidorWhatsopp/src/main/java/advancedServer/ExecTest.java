/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedServer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migue
 */
public class ExecTest {
    public static void main(String[] args) {
        File f = new File("src/main/java/img/cargarImg.jpg");
        InputStream in = null;
        Socket socketCliente = null;
        try(ServerSocket conexionServidor = new ServerSocket(1488);
                FileOutputStream fos = new FileOutputStream(f);
                ){
            
            socketCliente = conexionServidor.accept();
            System.out.println("Cliente conectado al servidor");
            in = socketCliente.getInputStream();
            
            byte[] buffer = new byte[4096];
            int bytesLeidos;

            while ((bytesLeidos = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesLeidos);
            }

            System.out.println("Imagen guardada con éxito");
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExecTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (socketCliente != null) {
                try {
                    socketCliente.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExecTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
