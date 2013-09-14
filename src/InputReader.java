

import java.io.*;
import java.net.*;
import java.util.*;

public class InputReader {
	final static File data = new File("data/data_project1");
	final static File query = new File("data/CS3246Project1_query.txt");
	//final static URL database = new URL("https://www.dropbox.com/sh/asngphsih4x5xrm/nebZcre8rF");
	
	public InputReader() {		
	}
	
	@SuppressWarnings("deprecation")
	public void readDatabase() {
		for (final File fileEntry :data.listFiles()) {
			if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	try {
	        		FileInputStream fileinput = new FileInputStream(fileEntry);
	        		BufferedInputStream mybuffer = new BufferedInputStream(fileinput);
	        		DataInputStream datainput = new DataInputStream(mybuffer);
	        		while (datainput.available() != 0) {
	        			System.out.println(SearchEngine.backendIndexing(datainput.readLine()));	
	        		}
	        		fileinput.close();
	        		mybuffer.close();
	        		datainput.close();
	        	} catch (FileNotFoundException e) {
	        	} catch (IOException e) {	
	        	}
	        }
		}		
	}
	
	@SuppressWarnings("deprecation")
	public Vector<String> readQuery() {	
		Vector<String> queries = new Vector<String>();
		if (query.isFile()==true ) {
			try {
				FileInputStream fileinput = new FileInputStream(query);
        		BufferedInputStream mybuffer = new BufferedInputStream(fileinput);
        		DataInputStream datainput = new DataInputStream(mybuffer);
        		while (datainput.available()!= 0) {
        			String line = datainput.readLine();
        			line = SearchEngine.backendIndexing(line);
        			if (line!= null || line.compareTo("")!=0) {
        				queries.add(line);
        			}
        			System.out.println(line);
        		}
			} catch (IOException e) {
			}
		} 
		return queries;
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
		//ir.readQuery();
		
		// testing
		//String output = SearchEngine.backendIndexing(ir.readDatabase());
	}
}
