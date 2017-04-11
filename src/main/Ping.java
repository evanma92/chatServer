package main;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;
import java.util.List;
import java.util.TimerTask;

public class Ping extends TimerTask {

	private TCPServer server;
	private Socket socket;
	
	public Ping(Socket socket, TCPServer server){
		this.socket = socket;
		this.server = server;
	}
	
	@Override
	public void run() {
		
		// Pings to that particular client that's currently active.
		Writer out;
		try {
			out = new OutputStreamWriter(this.socket.getOutputStream(), "UTF-8");
			out.write("From the server: PING. Please respond with 'PONG' or you will be removed from the server.\n");
            out.flush();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
		
		
}
