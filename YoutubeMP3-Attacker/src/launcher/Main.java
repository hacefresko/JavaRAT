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
		
		while(true) {
			server.connect();
			
			while(server.connectionIsOpen()) {
				try {
					System.out.print("\n\n> ");
					command = in.nextLine();
					CommandManager.parseCommand(command, server);
				}catch(IOException e){
					server.end();
					System.out.println("Connection interrupted :/ \n");
				}
			}
		}
	}
}
