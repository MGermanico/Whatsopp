/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedClient;

import java.io.DataInputStream;
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
public class ConnectionTest {
    public static void main(String[] args) {
        File f = new File("src/main/java/img/src.zip");
        System.out.println(f.getAbsolutePath() + " : existe = " + f.exists());
        OutputStream out = null;
        try(Socket socketCliente = new Socket("localhost", 1488);
                FileInputStream fis = new FileInputStream(f);
                ){
            System.out.println("Cliente conectado al servidor");
            out = socketCliente.getOutputStream();
            
            byte[] buffer = new byte[4096];
            int bytesLeidos;

            while ((bytesLeidos = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesLeidos);
            }

            System.out.println("Imagen enviada con éxito");
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(ConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
