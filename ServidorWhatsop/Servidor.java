import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
        public static void main(String[] args) {
            try (ServerSocket ss = new ServerSocket(1488);
                 Socket conexionCliente = ss.accept();
                     DataInputStream dis = new DataInputStream(conexionCliente.getInputStream());
                    ) {
                System.out.println("CLIENTE CONECTADO");
                // Leer una cadena de caracteres
                String cadena = dis.readUTF();
                System.out.println("Cadena: " + cadena);
                DataOutputStream out = new DataOutputStream(conexionCliente.getOutputStream());
                out.writeUTF("AQUI ESTA");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
