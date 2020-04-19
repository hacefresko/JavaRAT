package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket ss;
	
	private int _port;
	
	public Server(int port) throws IOException{
		ss = new ServerSocket(_port);
	}

	public Connection connect() throws IOException{
		System.out.println("ola");
		Socket s = ss.accept();
		System.out.println("ola");
		return new Connection(s);
	}
}
