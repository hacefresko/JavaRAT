package connection;

import java.io.File;
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
		    			//Waiting for auth as a sender socket (not connecting with hello)
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

	public void sendFile(File file) {
		Thread t = new Thread() {
	    	public void run() {
	    		Connection temp = null;
	    		String hello;
		    	try {
		    		do {
		    			temp = connect();
		    			hello = temp.receive();
		    		//Waiting for auth as a receiver socket (not connecting with hello)
		    		}while(hello.equals("hello"));
		    		temp.send(file);
		    	}catch(IOException e1){
		    		try{temp.end();}catch(IOException e2) {};
		    	}
	    	}
	    };
		t.start();
		try{t.join();}catch(InterruptedException e) {};
	}
}
