

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
	public Vector<Book> readDatabase() {
		Vector<Book> database = new Vector<Book>();
		
		int B = 0; // number of books recorded
		for (final File fileEntry :data.listFiles()) {
			if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	try {
	        		FileInputStream fileinput = new FileInputStream(fileEntry);
	        		BufferedInputStream mybuffer = new BufferedInputStream(fileinput);
	        		DataInputStream datainput = new DataInputStream(mybuffer);
	        		
	        		boolean recordName = false;
        			boolean recordDescription = false;
        			boolean recordPublishDate = false;
        			boolean recordAuthors = false;
        			boolean recordKeywords = false;
        			
        			String name = "";
	        		String description = ""; 
	        		String publish_date = "";
	        		
	        		while (datainput.available() != 0) {
	        			String line = datainput.readLine();
	        			line = SearchEngine.backendIndexing(line);
	        			
	        			
	        			
	        			/*if (!line.equals("")) {
	        				// read book's title
	        				if (!recordName) {
	        					recordName = true;
	        					name = line;
	        				} else if (!recordDescription && !line.substring(0, 3).equals("cacm")) {
	        					if (description.length()==0) {
	        						description = line;
	        					} else {
	        						description += " " + line;
	        					}
	        				} else if (line.substring(0, 3).equals("cacm")) {
	        					recordDescription = true;
	        					recordPublishDate = true;
	        					publish_date = line;
	        				} else if (recordPulishDate && !recordAuthors && !line.equals("")) {
	        					
	        				}
	        			}*/
	        			
	        			if (line.length()>0) {
	        				System.out.println(line);	
	        			}
	        		}
	        		fileinput.close();
	        		mybuffer.close();
	        		datainput.close();
	        	} catch (FileNotFoundException e) {
	        	} catch (IOException e) {	
	        	}
	        	B++;
	        }
		}		
		return database;
	}
	
	@SuppressWarnings("deprecation")
	public Vector<String> readQuery() {	
		Vector<String> queries = new Vector<String>();
		if (query.isFile()==true ) {
			try {
				FileInputStream fileinput = new FileInputStream(query);
        		BufferedInputStream mybuffer = new BufferedInputStream(fileinput);
        		DataInputStream datainput = new DataInputStream(mybuffer);
        		int Q = 0; // number of queries
        		while (datainput.available()!= 0) {
        			String line = datainput.readLine();
        			line = SearchEngine.backendIndexing(line);
        			line = line.replaceAll("(?m)^[ \t]*\r?\n", "");
        			
        			// recognize query syntax and blank line
        			if (line.length()<=3 && line.length() > 1) {
        				Q++;
        				line = "";
        			}
        			// save line to queries
        			if (!line.equals("")) {
        				if (Q > queries.size() ) {
        					queries.add(line);
        				} else {
        					queries.set(Q-1, queries.get(Q-1) + " " + line);
        				}
        			}
        		}
			} catch (IOException e) {
			}
		} 
		for (String query : queries) {
			System.out.println(query);
		}
		return queries;
	}
	
	private String removeQuerySyntax(String line) {
		if (line.length()<=3) {
			line = "";
		}
		return line;
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
		//ir.readDatabase();
		ir.readQuery();
		
		// testing
		//String output = SearchEngine.backendIndexing(ir.readDatabase());
	}
}
