/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package advancedServer;

import java.net.Socket;

/**
 *
 * @author migue
 */
class ClientHandler implements Runnable{

    private Socket socket;
    
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        
    }
    
}
