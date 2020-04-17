package commands;

import java.io.IOException;

import control.Controller;

public class CommandManager {
	private static Command[] commands = {new EndCommand(), new SendCommand()};
	
	public static void parseCommand(String command, Controller ctrl) throws IOException {
		boolean parsed = false;
		int i = 0;
		
		while(!parsed && i < commands.length) {
			try {
				if(commands[i].parse(command)) {
					commands[i].execute(ctrl);
					parsed = true;
				}
			}catch(Exception e) {
				ctrl.sendMsg("Please, input a valid command >:(\nType help for more info");
				parsed = true;
			}
			i++;
		}
		if(!parsed) {
			ctrl.execute(command);
		}
	}
}
