package commands;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import connection.Server;

public class SendCommand extends Command{
	private String _command;
	
	public SendCommand() {
		super("send", "\"file/dir\"", "compress and sends the specified file/dir");
	}

	protected boolean parse(String command) {
		boolean ok = false;
		
		if(command.contains(_commandName)) {
			_command = command;
			ok = true;
		}
		
		return ok;
	}
	
	@Override
	public void execute(Server server) throws IOException {
		System.out.println(server.send(_command));
		
		int length = Integer.parseInt(server.receive());
		byte [] mybytearray  = new byte [length];
		
		FileOutputStream fos = new FileOutputStream(new File("received.zip"));
		BufferedOutputStream bos = new BufferedOutputStream(fos);
	    int bytesRead = server.receiveFile(mybytearray,0,mybytearray.length);
	    int current = bytesRead;

	    do {
	    	bytesRead = server.receiveFile(mybytearray, current, (mybytearray.length-current));
		    if(bytesRead >= 0) {
		    	current += bytesRead;
		     }
	    } while(bytesRead > -1);

	    bos.write(mybytearray, 0 , current);
	    bos.flush();
	    
	    if (fos != null) fos.close();
	    if (bos != null) bos.close();
	    
	    System.out.println(server.receive());
	}
}