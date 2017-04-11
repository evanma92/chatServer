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
import java.util.TimerTask;

public class Pong extends TimerTask {
	private Ping ping;
	private Socket socket;
	private String line = "";
	private Timer pongTimer;
	
	public Pong(Ping ping, Socket socket, Timer timer){
		this.ping = ping;
		this.socket = socket;
		this.pongTimer = timer;
	}
	
	public void run() {
		
		System.out.println("Checking for PONG");
		InputStream inputStream = null;
		BufferedReader buffReader = null;
		
		try {
			inputStream = socket.getInputStream();
			buffReader = new BufferedReader(new InputStreamReader(inputStream));
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		while(true){
			try {
				this.line = buffReader.readLine();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Does the input equal PONG?: " + this.line.equals("PONG"));
			if (this.line.equals("PONG")){
				this.ping.setIsPongCompleted(true);
				this.pongTimer.cancel();
				this.pongTimer.purge();
				break;
			}
		}
	}

}
