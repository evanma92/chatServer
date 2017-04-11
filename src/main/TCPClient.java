package main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient extends Thread {
	static final int PORT = 55556;
	protected String hostName;
	
	public TCPClient(String givenName){
		this.hostName = givenName;
	}
	
	@Override
	public void run(){
		System.out.println("Running client");
		
		try {
            Socket s = new Socket(this.hostName, PORT);
            System.out.println("Connected to server");
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(s.getOutputStream()));

//            while (true) 
            for (int i=0; i<3; i++)
            {
                out.write("Hello World!");
                out.newLine();
                out.flush();

                Thread.sleep(200);
            }
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
}
