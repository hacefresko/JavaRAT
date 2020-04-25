package commands;

import java.io.IOException;

import connection.Controller;

public class EndCommand extends Command{

	public EndCommand() {
		super("endcon");
	}

	@Override
	public void execute(Controller ctrl) throws IOException {
		ctrl.sendMsg("Bye :(\n\n");
		ctrl.endConnection();
	}
}
