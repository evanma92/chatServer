package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.List;
import java.util.Timer;

public class TCPServerThreads extends Thread{
	private Socket socket;
	private TCPServer server;
	
	public TCPServerThreads(Socket clientSocket, TCPServer server){
		this.socket = clientSocket;
		this.server = server;
	}
	
	@Override
	public void run(){
		InputStream inputStream = null;
		BufferedReader buffReader = null;
		
		Ping ping = new Ping(this.socket, this.server);
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(ping, 30000, 30000);
		
		try {
			inputStream = socket.getInputStream();
			buffReader = new BufferedReader(new InputStreamReader(inputStream));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String line;
		
		while(true){
				
			try {
				// Reads from the InputStream
				line = buffReader.readLine();
				// Gets a list of active clients
				List<Socket> activeSockets = server.getActiveSockets();
				if ((line == null) || line.equalsIgnoreCase("QUIT")){
					activeSockets.remove(socket);
					socket.close();
				} else {
					// Gets the Name of the client as well as the index for easy identification. 
					// Delete the index when you have unique clients
					String senderName = this.socket.getInetAddress().getHostName() + 
											activeSockets.indexOf(this.socket); 
					
					// Broadcasts to each client that's currently active.
					for (int i=0; i<activeSockets.size(); i++){
						if (!activeSockets.get(i).equals(this.socket)){
							Writer out = new OutputStreamWriter(activeSockets.get(i).getOutputStream(), "UTF-8");
							out.write(senderName + ": " + line + "\n");
			                out.flush();
						} else {
							Writer out = new OutputStreamWriter(this.socket.getOutputStream(), "UTF-8");
							out.write("Your message was sent!\n");
			                out.flush();
						}
					}
					
					System.out.println(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
			
		}
		
	}
	
}
