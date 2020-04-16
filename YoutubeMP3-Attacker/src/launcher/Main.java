package launcher;

import java.io.IOException;
import java.util.Scanner;

import commands.CommandManager;
import connection.Server;

public class Main {

	public static void main(String[] args) throws IOException {
		Server server = new Server(5123);
		Scanner in = new Scanner(System.in);
		String command;
		
		System.out.println(asciiArt());
		
		while(true) {
			server.connect();
			
			while(server.connectionIsOpen()) {
				try {
					System.out.print("\n\n> ");
					CommandManager.parseCommand(in.nextLine(), server);
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
