import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
        public static void main(String[] args) {
            DataInputStream dis;
            try (ServerSocket ss = new ServerSocket(1488);
                 Socket conexionCliente = ss.accept();
                    ) {
                System.out.println("CLIENTE CONECTADO");
                // Leer una cadena de caracteres
                dis = new DataInputStream(conexionCliente.getInputStream());
                String cadena = dis.readUTF();
                System.out.println("Cadena: " + cadena);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
