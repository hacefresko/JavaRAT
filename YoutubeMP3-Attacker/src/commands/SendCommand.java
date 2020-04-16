package commands;

import java.io.IOException;

import connection.Server;

public class SendCommand extends Command{
	private String _file;

	public SendCommand() {
		super("send", "\"file/dir\"", "compress and sends the specified file/dir to the default email");
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
		String destFile;
		
		//Compress the file
		if(_file.contains(".")) {
			destFile = _file.split("[.]")[0];
		}
		else {
			destFile = _file;
		}
		destFile += ".zip";
		System.out.println("Compressing " + _file + " to " + destFile + " ...");
		server.send("Compress-Archive -Path " + _file + " -CompressionLevel Optimal -DestinationPath " + destFile);
		
		//Send the file
		
	}

}
