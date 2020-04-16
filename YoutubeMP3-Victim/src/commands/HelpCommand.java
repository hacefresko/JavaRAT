package commands;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import control.Controller;

public class HelpCommand extends Command{

	public HelpCommand() {
		super("help", null, "Show this message + PowerShell help message");
	}

	@Override
	public void execute(Controller ctrl) throws IOException {
		String helpMessage;
		
		helpMessage = "\nAvailable commands:\n";
		helpMessage += CommandManager.help();
		helpMessage += "\nDisplay PowerShell help message? [y/n] (enter to skip)";
		ctrl.sendMsg(helpMessage);
		String op = ctrl.receiveMsg();
		if(op.equals("y")) {
			ctrl.execute("help");
		}else {
			ctrl.sendMsg("");
		}
	}
}
