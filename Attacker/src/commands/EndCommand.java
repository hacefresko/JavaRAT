package commands;

import java.io.IOException;

import connection.Connection;
import connection.Server;

public class EndCommand extends Command{

	public EndCommand() {
		super("endcon", null, "ends the connection");
	}

	@Override
	public void execute(Connection con, Server server) throws IOException {
		System.out.println(con.send(_commandName));
		con.end();
	}

}
