package commands;

import java.io.IOException;

import connection.Connection;
import connection.Server;

public class CommandManager {
	private static Command[] commands = {new HelpCommand(), new EndCommand(), new GetCommand()};
	
	public static void parseCommand(String command, Connection con, Server server) throws IOException, InterruptedException {
		boolean parsed = false;

		for(Command c : commands) {
			if(c.parse(command)) {
				c.execute(con, server);
				parsed = true;
			}
		}
		if(!parsed) {
			System.out.println(con.send(command));
		}
	}
	
	protected static String help() {
		String helpMessage = "";
		
		for(Command c : commands) {
			helpMessage += c.getHelp();
		}
		
		return helpMessage;
	}
}
