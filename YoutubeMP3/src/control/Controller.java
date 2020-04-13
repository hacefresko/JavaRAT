package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
	public static String execute(String com) throws IOException {
		String command = "cmd  " + com;
		String line, out = "";
		
		Process powerShellProcess = Runtime.getRuntime().exec(command);
		powerShellProcess.getOutputStream().close();
		
		//Standard output
		BufferedReader stdout = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
		while ((line = stdout.readLine()) != null) {
			 out += line + "\n";
		}
		stdout.close();
		
		//Error output
		BufferedReader stderr = new BufferedReader(new InputStreamReader(powerShellProcess.getErrorStream()));
		while ((line = stderr.readLine()) != null) {
			 out += line + "\n";
		}
		stderr.close();

		return out;
	}
}
