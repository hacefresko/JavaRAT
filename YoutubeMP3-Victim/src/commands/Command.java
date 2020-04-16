package commands;

import java.io.IOException;

import control.Controller;

public abstract class Command {
	protected String _commandName;
	protected String _argument;
	protected String _help;
	
	public Command(String commandName, String argument, String help) {
		_commandName = commandName;
		_argument = argument;
		_help = help;
	}
	
	public abstract void execute(Controller ctrl) throws IOException;

	protected boolean parse(String command) {
		return _commandName.equals(command);
	}
	
	protected String getHelp() {
		return "   [" + _commandName + "]" + (_argument==null ? "" : ("[" + _argument + "]")) + ": " + _help + "\n";
	}
}
