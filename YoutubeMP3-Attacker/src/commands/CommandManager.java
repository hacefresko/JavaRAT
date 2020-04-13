package commands;

import java.io.IOException;

import connection.Server;

public class CommandManager {
	private static Command[] commands = {new HelpCommand(), new EndCommand()};
	
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
	
	protected static void help(Server server) throws IOException {
		String helpMessage = "\nAvailable commands:\n";
		
		for(Command c : commands) {
			helpMessage += "   [" + c._commandName + "]: " + c._help + "\n";
		}
		helpMessage += "\n PowerShell help message:" + server.send("help");
		
		System.out.println(helpMessage);
	}
}
