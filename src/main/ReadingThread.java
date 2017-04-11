package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.List;

public class ReadingThread extends Thread {
	private Socket socket;
	private TCPServer server;
	
	public ReadingThread(Socket clientSocket, TCPServer server){
		this.socket = clientSocket;
		this.server = server;
	}
	
	@Override
	public void run(){	
//		System.out.println("Thread for client started");
		InputStream inputStream = null;
		BufferedReader buffReader = null;
//		PrintWriter printWriter = null;
		
		try {
			inputStream = socket.getInputStream();
			buffReader = new BufferedReader(new InputStreamReader(inputStream));
//			printWriter = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String line;
		
		while(true){
			try {
				line = buffReader.readLine();
				List<Socket> activeSockets = server.getActiveSockets();
				if ((line == null) || line.equalsIgnoreCase("QUIT")){
					activeSockets.remove(socket);
					socket.close();
				} else {
					for (int i=0; i<activeSockets.size(); i++){
						Writer out = new OutputStreamWriter(activeSockets.get(i).getOutputStream(), "UTF-8");
						out.write(line);
		                out.flush();
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
