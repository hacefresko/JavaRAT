package connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	private Socket s;
	private String _ip;
	private int _port;
	private DataOutputStream dout;
	private DataInputStream din;
	
	public Client(String hostIP, int port) {
		_ip = hostIP;
		_port = port;
	}
	
	public boolean isClosed() {
		return s.isClosed();
	}
	
	public void send(String msg) throws IOException {
		dout.writeUTF(msg);
		dout.flush();
	}
	
	public void sendFile(File file) throws IOException {
		// Auth to identify as a sender socket
		send(file.getName());
		
		int lenght = (int) file.length();
		send(String.valueOf(lenght));
		
		byte [] mybytearray  = new byte [(int)file.length()];
		
		InputStream in = new FileInputStream(file);
		BufferedInputStream bin = new BufferedInputStream(in);
		OutputStream out = s.getOutputStream();
        
        bin.read(mybytearray, 0, mybytearray.length);
        out.write(mybytearray,0, mybytearray.length);
        
        //out.close() directly shuts down the socket
        out.close();
        in.close();
        bin.close();
	}
	
	public String receive() throws IOException {
		return din.readUTF();
	}
	
	public void receiveFile(String fileName) throws IOException {
		InputStream is;
		DataInputStream din;
		FileOutputStream out;
		BufferedOutputStream bos;
		
		//Auth to identify as a receiver socket
		send("iwtraf :(");
		
		try {
			is = s.getInputStream();
		    din = new DataInputStream(is);
		    out = new FileOutputStream(new File(fileName));
		    bos = new BufferedOutputStream(out);
		} catch(IOException e) {
			send("Error: There was a problem initializing the transfer");
			throw e;
		}
		try {
		    //Server sends length of file
		    int length = Integer.valueOf(din.readUTF());
		    byte [] mybytearray  = new byte [length];
		    
		    send("Sending " + fileName + " (" + length + " bytes)");
		    
		    //is.read tries to read up to length, but may read less
		    int bytesRead = is.read(mybytearray, 0, length);
		    int current = bytesRead;
		    
		    while (current != length) {
		    	send(current + "/" + length);
		    	bytesRead = is.read(mybytearray, current, (length - current));
		    	if(bytesRead >= 0) {
		    		current += bytesRead;
		    	}
		    }
		    
		    bos.write(mybytearray, 0 , length);
		    bos.flush();
		    
		    send("Transfer completed");
		}catch(IOException e) {
			send("Error: The transfer couldn't be completed");
		}
	    
	    is.close();
		out.close();
		bos.close();
	}
	
	public void end() throws IOException {
		s.close();
	}
	
	public void connect() throws IOException {
		reset();
		dout.writeUTF("hello");
	}
	
	public void reset() throws IOException {
		while(!initConnection());
		System.gc(); //Calls the garbage collector because of the previous function
		dout = new DataOutputStream(s.getOutputStream());
		din = new DataInputStream(s.getInputStream());
	}
	
	private boolean initConnection() {
        try{
            s = new Socket(_ip,_port);
            return true;
        }
        catch(Exception err){
        	return false;
        }
    }
}
