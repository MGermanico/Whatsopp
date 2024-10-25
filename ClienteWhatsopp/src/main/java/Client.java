
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
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
public class Client {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String nombre;

    public Client(Socket socket, String nombre) {
        try {
            this.socket = socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nombre = nombre;
        } catch (IOException e) {
            cerrarTodo(socket, reader, writer);
        }
    }

    public void enviarMensaje() {
        try (Scanner scanner = new Scanner(System.in);) {
            writer.write(nombre);
            writer.newLine();
            writer.flush();
            while (socket.isConnected()) {
                String mensaje = scanner.nextLine();
                if (mensaje.equals("*")) {
                    socket.close();
                    System.exit(0);
                } else {
                    writer.write(nombre + ": " + mensaje);
                    writer.newLine();
                    writer.flush();
                }
            }
        } catch (IOException ex) {
            cerrarTodo(socket, reader, writer);
        }
    }

    Thread listening;

    public void listenForMessage() {
        listening = new Thread(new Runnable() {
            @Override
            public void run() {
                String mensajeDelGrupo;

                while (socket.isConnected()) {

                    try {
                        mensajeDelGrupo = reader.readLine();
                        System.out.println(mensajeDelGrupo);
                    } catch (IOException e) {
                        cerrarTodo(socket, reader, writer);
                    }
                }
            }
        });
        listening.start();

    }

    public void cerrarTodo(Socket socket, BufferedReader reader, BufferedWriter writer) {

        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listening.interrupt();

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce tu nombre de usuario: ");
        String nombre = sc.nextLine();

        try (Socket socket = new Socket("localhost", 1488);) {
            Client client = new Client(socket, nombre);
            client.listenForMessage();
            client.enviarMensaje();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
