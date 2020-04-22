package launcher;

import java.io.IOException;

import connection.Controller;

public class Main {
	public static void main(String[] args) throws IOException {
		//Controller ctrl = new Controller("0.tcp.ngrok.io", 14235);
		Controller ctrl = new Controller("192.168.0.159", 5123);
		ctrl.run();
	}
}