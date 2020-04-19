package commands;

import java.io.IOException;
import java.util.Scanner;

import connection.Server;

public class HelpCommand extends Command{

	public HelpCommand() {
		super("help", null, "Show this message + PowerShell help message");
	}

	@Override
	public void execute(Server server) throws IOException {
		Scanner in = new Scanner(System.in);
		
		System.out.println("\nAvailable commands:\n");
		System.out.println(CommandManager.help());
		System.out.println("\nDisplay PowerShell help message? [y/n] (enter to skip)");
		String op = in.nextLine();
		if(op.equals("y")) {
			System.out.println(server.send("help"));
		}
	}
}
