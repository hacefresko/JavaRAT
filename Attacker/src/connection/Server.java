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
	
	public void receiveFile() {
		Thread t = new Thread() {
	    	public void run() {
	    		Connection temp = null;
	    		String fileName;
		    	try {
		    		do {
		    			temp = connect();
		    			fileName = temp.receive();
		    		}while(fileName.equals("hello"));
		    		temp.receive(fileName);
		    	}catch(IOException e1){
		    		try{temp.end();}catch(IOException e2) {};
		    	}
	    	}
	    };
		t.start();
		try{t.join();}catch(InterruptedException e) {};
	}
}
