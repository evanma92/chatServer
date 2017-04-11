package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Ping extends TimerTask {
	private boolean isPongCompleted = false;
	private TCPServer server;
	private Socket socket;
	private Timer pingTimer;
	
	public Ping(Socket socket, TCPServer server, Timer timer){
		/* 
		 * socket: to write to the client
		 * server: to get a list of active sockets
		 * timer: to cancel the pingTimer to stop pinging the client
		 */
		this.socket = socket;
		this.server = server;
		this.pingTimer = timer;
	}
	
	public void cancelPingTimer(Timer pingTimer){
		pingTimer.cancel();
		pingTimer.purge();
	}
	
	public Timer getPingTimer(){
		return this.pingTimer;
	}
	
	
	public void setIsPongCompleted(boolean bool){
		this.isPongCompleted = bool;
	}
	
	public boolean getIsPongCompleted(){
		return this.isPongCompleted;
	}
	
	@Override
	public void run() {

		// Pings to that particular client that's currently active.
		Writer out;
		try {
			out = new OutputStreamWriter(this.socket.getOutputStream(), "UTF-8");
			out.write("From the server: PING. Please respond with 'PONG' or you will be removed from the server.\n");
            out.flush();
            
            // Wait for the PONG feedback from the user
            Timer timer = new Timer();
            Pong pong = new Pong(this, this.socket, timer);
            Wait wait = new Wait(timer,this, this.socket, this.server);
            wait.start();
            timer.schedule(pong, 0);          
            
            
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
		
		
}
