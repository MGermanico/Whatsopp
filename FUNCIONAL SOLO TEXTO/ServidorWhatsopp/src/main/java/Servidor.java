
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author migue
 */
public class Servidor {
    
    private ServerSocket serverSocket;

    public Servidor(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    public void startServer(){
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("Se ha unido un nuevo usuario!");
                ClientHandler clientHandler = new ClientHandler(socket);
                System.out.println("-----");
                
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void closeServer(){
        try{
            if (serverSocket != null) {
                serverSocket.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
