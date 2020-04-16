package commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import control.Controller;

public class SendCommand extends Command{
	private String _fileName;
	
	public SendCommand() {
		super("send");
	}

	protected boolean parse(String command) {
		boolean ok = false;
		
		if(command.contains(_commandName)) {
			try {
				_fileName = command.split("\"")[1];
			}catch(Exception e) {
				throw new IllegalArgumentException();
			}
			ok = true;
		}
		
		return ok;
	}
	
	@Override
	public void execute(Controller ctrl) throws IOException {
		send(compress(_fileName, ctrl), ctrl);
	}

	private String compress(String fileName, Controller ctrl) throws IOException {
		try {
			File file = new File(fileName);
			String zipFileName = file.getName().concat(".zip");
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));
			zos.putNextEntry(new ZipEntry(file.getName()));
			byte[] bytes = Files.readAllBytes(Paths.get(fileName));
	        zos.write(bytes, 0, bytes.length);
	        zos.closeEntry();
	        zos.close();
	        return zipFileName;
		} catch (FileNotFoundException ex) {
			ctrl.sendMsg("The file " + fileName + " does not exist");
			return null;
	    } catch (IOException ex) {
	    	ctrl.sendMsg("I/O error: " + ex);
	    	return null;
	    }
	}
	
	private void send(String file, Controller ctrl) throws IOException {
		
	}
	
}
