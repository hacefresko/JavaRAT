package launcher;

import java.io.IOException;
import java.util.Scanner;

import commands.CommandManager;
import connection.Server;

public class Main {

	public static void main(String[] args) throws IOException {
		int _port = 5123;
		
		Server server = new Server(_port);
		Scanner in = new Scanner(System.in);
		
		System.out.println(asciiArt());
		
		while(true) {
			System.out.println("Waiting for connection on port " + _port + "...");
			server.connect();
			System.out.println("Connected");
			System.out.println("Retrieving system info...");
			System.out.println(server.getSysInfo());
				
			while(server.connectionIsOpen()) {
				try {
					System.out.print("\n\n> ");
					String command = in.nextLine();
					CommandManager.parseCommand(command, server);
				}catch(IOException e){
					server.end();
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
