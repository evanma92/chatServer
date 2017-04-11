package main;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        TCPServer server = new TCPServer();
        server.start();    
        
//        for (int i=0; i<3; i++){
//	        TCPClient client = new TCPClient("localhost");
//	        client.start();
//        }
    }
}