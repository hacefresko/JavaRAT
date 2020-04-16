package commands;

import java.io.IOException;

import control.Controller;

public class CommandManager {
	private static Command[] commands = {new EndCommand(), new SendCommand()};
	
	public static void parseCommand(String command, Controller ctrl) throws IOException {
		boolean parsed = false;

		for(Command c : commands) {
			try {
				if(c.parse(command)) {
					c.execute(ctrl);
					parsed = true;
				}
			}catch(Exception e) {
				ctrl.sendMsg("Please, input a valid command >:(\nType help for more info");
			}
		}
		if(!parsed) {
			ctrl.execute(command);
		}
	}
}
