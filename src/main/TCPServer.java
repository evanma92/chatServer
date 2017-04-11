package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPServer extends Thread{
	private List<String> activeConnections = new ArrayList <String>();
	private List<Socket> activeSockets = new ArrayList<Socket>();
	public final int PORT = 55557;
	
	public List<Socket> getActiveSockets(){
		return this.activeSockets;
	}
	
	@Override
	public void run(){

		ServerSocket ss = null;
		
		try {
			ss = new ServerSocket(PORT);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        while(true){
            Socket client = null;
            String clientName = "";
			try {
				client = ss.accept();
				clientName = client.getInetAddress().getHostName();
//				if (activeConnections.contains(clientName)){
//					System.err.println("Client exists. Closing the server");
//	            	ss.close();
//	            	break;
//				}
				
				activeConnections.add(clientName);
				activeSockets.add(client);
				System.out.println(clientName + " Accepted");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			new TCPServerThreads(client, this).start();
			
        }
	}
	
	
}
