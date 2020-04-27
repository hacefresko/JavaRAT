package commands;

import java.io.IOException;

import connection.Controller;

public abstract class Command {
	protected String _commandName;
	
	public Command(String commandName) {
		_commandName = commandName;
	}
	
	public abstract void execute(Controller ctrl) throws IOException;

	protected boolean parse(String command) {
		return _commandName.equals(command);
	}
}
