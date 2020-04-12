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
			
			while(true) {
				try {
					System.out.print("> ");
					server.send(in.nextLine());
				}catch(IOException e){
					System.out.println("Connection interrupted :/");
					break;
				}
			}
			System.out.print("Connect? [yes/no] > ");
			op = in.nextLine();
		}while(op.equals("yes"));
	}
}
