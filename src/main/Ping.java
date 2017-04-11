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
	
	public Ping(TCPServer server){
		this.server = server;
	}
	
	@Override
	public void run() {
		// Gets a list of active clients
		List<Socket> activeSockets = server.getActiveSockets();
		
		// Pings to each client that's currently active.
		for (int i=0; i<activeSockets.size(); i++){
			Writer out;
			try {
				out = new OutputStreamWriter(activeSockets.get(i).getOutputStream(), "UTF-8");
				out.write("From " + server.getName() + ": PING\n");
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
	
}
