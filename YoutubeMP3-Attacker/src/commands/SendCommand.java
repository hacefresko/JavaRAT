package commands;

import java.io.IOException;

import connection.ServerSide;

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
	public void execute(ServerSide server) throws IOException {
		System.out.println(server.send(_command));
		String fileName = server.receive();
		System.out.println("Transferred complete: " + server.receive(fileName) + " bytes were sent");
	}
}