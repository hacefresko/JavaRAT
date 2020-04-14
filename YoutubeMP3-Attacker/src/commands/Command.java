package commands;

import java.io.IOException;

import connection.Server;

public abstract class Command {
	protected String _commandName;
	protected String _help;
	
	public Command(String commandName, String help) {
		_commandName = commandName;
		_help = help;
	}
	
	public abstract void execute(Server server) throws IOException;

	protected boolean parse(String command) {
		return _commandName.equals(command);
	}
	
	protected String getHelp() {
		return "   [" + _commandName + "]: " + _help + "\n";
	}
}
