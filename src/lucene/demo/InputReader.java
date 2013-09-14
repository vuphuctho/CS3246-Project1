package lucene.demo;

import java.io.*;
import java.net.*;
import java.util.*;

public class InputReader {
	final static File folder = new File("C:\\Users\\Vu Phuc Tho\\Dropbox\\download\\data_project1");
	
	@SuppressWarnings("deprecation")
	public InputReader() throws IOException {
		for (final File fileEntry :folder.listFiles()) {
			if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	try {
	        		FileInputStream fileinput = new FileInputStream(fileEntry);
	        		BufferedInputStream mybuffer = new BufferedInputStream(fileinput);
	        		DataInputStream datainput = new DataInputStream(mybuffer);
	        		while (datainput.available() != 0) {
	        			System.out.println(datainput.readLine());
	        		}
	        		fileinput.close();
	        		mybuffer.close();
	        		datainput.close();
	        	} catch (FileNotFoundException e) {
	        	} catch (IOException e) {	
	        	}
	        	//System.out.println(fileEntry.getName());
	        }
		}		
	}
	
	public void readDatabase() {
	}
	
	public void readQuery() {		
	}
	
	public void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}
	
	public static void main(String[] args) throws IOException {
		InputReader ir = new InputReader();
		ir.readDatabase();
	}
}
