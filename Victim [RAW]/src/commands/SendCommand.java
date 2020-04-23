package commands;

import java.io.IOException;

import connection.Controller;

public class SendCommand extends Command{
	private String _fileName;

	public SendCommand() {
		super("send");
	}

	protected boolean parse(String command) {
		boolean ok = false;
		
		if(command.contains(_commandName)) {
			try {
				_fileName = command.split("\"")[1];
			}catch(Exception e) {
				throw new IllegalArgumentException();
			}
			ok = true;
		}
		
		return ok;
	}
	
	@Override
	public void execute(Controller ctrl) throws IOException {
		ctrl.sendMsg("Sending " + _fileName);
		ctrl.receiveFile(_fileName);
		//Extract file
	}

}
