package commands;

import java.io.IOException;

import control.Controller;

public class CommandManager {
	private static Command[] commands = {new HelpCommand(), new EndCommand(), new SendCommand()};
	
	public static void parseCommand(String command, Controller ctrl) throws IOException {
		boolean parsed = false;

		for(Command c : commands) {
			if(c.parse(command)) {
				c.execute(ctrl);
				parsed = true;
			}
		}
		if(!parsed) {
			ctrl.execute(command);
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
