package commands;

import java.io.IOException;

import connection.Connection;
import connection.Server;

public class SendCommand extends Command{
	private String _fileName;
	
	public SendCommand() {
		super("send", "\"file/dir\"", "sends the specified file/dir to the victim's machine and uncompress it");
	}

	protected boolean parse(String command) {
		boolean ok = false;
		
		if(command.contains(_commandName)) {
			_fileName = command.split("\"")[1];
			ok = true;
		}
		
		return ok;
	}
	
	@Override
	public void execute(Connection con, Server server) throws IOException, InterruptedException {

	}

}
