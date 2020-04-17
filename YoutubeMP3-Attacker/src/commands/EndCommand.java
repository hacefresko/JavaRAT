package commands;

import java.io.IOException;

import connection.ServerSide;

public class EndCommand extends Command{

	public EndCommand() {
		super("endcon", null, "ends the connection");
	}

	@Override
	public void execute(ServerSide server) throws IOException {
		System.out.println(server.send(_commandName));
		server.end();
	}

}
