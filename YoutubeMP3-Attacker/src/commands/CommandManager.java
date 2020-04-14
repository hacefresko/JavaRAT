package commands;

import java.io.IOException;
import java.util.Scanner;

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
		Scanner in = new Scanner(System.in);
		
		System.out.println("\nAvailable commands:\n");
		for(Command c : commands) {
			System.out.println("   [" + c._commandName + "]: " + c._help);
		}
		System.out.println("\nDisplay PowerShell help message? [yes/no]");
		String op = in.nextLine();
		if(op.equals("yes")) {
			System.out.println(server.send("help"));
		}
	}
}
