package launcher;

import java.io.IOException;
import java.util.Scanner;

import connection.Server;

public class Main {

	public static void main(String[] args) throws IOException {
		Server server = new Server(1234);
		Scanner in = new Scanner(System.in);
		String op;
		
		do {
			server.connect();
			
			while(!server.isClosed()) {
				try {
					System.out.print("> ");
					server.send(in.nextLine());
				}catch(IOException e){
					server.end();
					System.out.println("Connection interrupted :/");
				}
			}
			
			System.out.print("Try to reconnect? [yes/no] > ");
			op = in.nextLine();
			
		}while(op.equals("yes"));
	}
}
