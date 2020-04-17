package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.io.IOUtils;


public class ClientSide {
	private Socket s;
	private String _ip;
	private int _port;
	private DataOutputStream dout;
	private DataInputStream din;
	
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
	
	public void send(File file) throws IOException {
		OutputStream out = s.getOutputStream();
		InputStream in = new FileInputStream(file);
		IOUtils.copyLarge(in, out);
		out.flush();
		out.close();
	}
	
	public void end() throws IOException {
		try{s.close(); dout.close(); din.close();}catch (NullPointerException ex) {}
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
