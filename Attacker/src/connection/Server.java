package connection;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	private ServerSocket ss;
	
	public Server(int port) throws IOException{
		ss = new ServerSocket(port);
	}

	public Connection connect() throws IOException{
		return new Connection(ss.accept());
	}
}
