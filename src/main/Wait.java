package main;

import java.net.Socket;
import java.util.List;
import java.util.Timer;

public class Wait extends Thread {
	private Timer timer;
	private Ping ping;
	private Socket socket;
	private TCPServer server;

	public Wait(Timer timer, Ping ping, Socket socket, TCPServer server){
		this.timer = timer;
		this.ping = ping;
		this.socket = socket;
		this.server = server;
	}
	
	@Override
	public void run(){
		// In the meantime we wait for 5s
        try {
			System.out.println("Timer has started");
        	sleep(5*1000);
        	System.out.println("Timer is done");
        	
        	// If the user fails to respond with a PONG...
            if (ping.getIsPongCompleted() == false){
	    		// Gets a list of active clients
	    		List<Socket> activeSockets = this.server.getActiveSockets();
	    		// Remove the socket if it did not respond with PONG
	    		System.out.println("Since " + this.socket.getInetAddress().getHostName() + " "
	    				+ "did not respond with PONG, it will be removed from the database");
	    		activeSockets.remove(this.socket);
	    		
	    		// If there are no more active sockets, stop pinging
	    		if (this.server.getActiveSockets().isEmpty()){
		    		ping.cancelPingTimer(ping.getPingTimer());
	    		}
            }
			timer.cancel();
			timer.purge();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
