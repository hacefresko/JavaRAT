package commands;

import java.io.IOException;

import connection.Server;

public class HelpCommand extends Command{

	public HelpCommand() {
		super("help", "Show this message + PowerShell help message");
	}

	@Override
	public void execute(Server server) throws IOException {
		CommandManager.help(server);
	}
}
