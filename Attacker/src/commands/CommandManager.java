package commands;

import java.io.IOException;

import connection.Server;

public class CommandManager {
	private static Command[] commands = {new HelpCommand(), new EndCommand(), new SendCommand()};
	
	public static void parseCommand(String command, Server server) throws IOException {
		boolean parsed = false;

		for(Command c : commands) {
			if(c.parse(command)) {
				c.execute(server);
				parsed = true;
			}
		}
		if(!parsed) {
			System.out.println(server.send(command));
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
