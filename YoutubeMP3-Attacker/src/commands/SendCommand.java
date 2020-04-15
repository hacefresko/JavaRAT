package commands;

import java.io.IOException;

import connection.Server;

public class SendCommand extends Command{
	private String _file;

	public SendCommand(String commandName, String help) {
		super("send", "compress and sends the data to the default email");
	}
	
	public boolean parse(String input) {
		boolean ok = false;
		String command = input.split(" ")[0];
		
		if(command.equals(_commandName)) {
			ok = true;
			_file = input.split("\"")[1];
		}
		
		return ok;
	}

	@Override
	public void execute(Server server) throws IOException {
		//Compress the file
		String destFile = _file.split(".")[0];
		destFile += ".zip";
		server.send("Compress-Archive -Path " + _file + " -CompressionLevel Optimal -DestinationPath " + destFile);
		
		//Send the file
	}

}
