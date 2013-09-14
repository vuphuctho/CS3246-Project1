package lucene.demo;

import java.io.*;
import java.net.*;
import java.util.*;

public class InputReader {
	final static File folder = new File("C:\\Users\\Vu Phuc Tho\\Dropbox\\download\\data_project1");
	
	public InputReader() throws FileNotFoundException {
		for (final File fileEntry :folder.listFiles()) {
			if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	try {
	        		BufferedReader in = new BufferedReader(new FileReader(fileEntry.getPath())); 
	        	} catch (FileNotFoundException e) {}
	        	System.out.println(fileEntry.getName());
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
	
	public static void main(String[] args) throws FileNotFoundException {
		InputReader ir = new InputReader();
		ir.readDatabase();
	}
}
