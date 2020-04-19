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
	public void execute(Connection con, Server server) throws IOException {
		String response = con.send(_command);
		System.out.println(response);
		
		if(response.contains("File compressed")) {
			String fileName = con.receive();

			Connection temporary = server.connect();
			temporary.receive(fileName);
			temporary.end();
			System.out.println("Process completed");
		}
	}
}