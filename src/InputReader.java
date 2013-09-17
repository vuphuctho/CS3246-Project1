

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Pattern;

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
        			boolean recordPublishDate = false;
        			
        			String name = "";
	        		String keywords = ""; 
	        		String publish_date = "";
	        		
	        		while (datainput.available() != 0) {
	        			String line = datainput.readLine();
	        			line = SearchEngine.backendIndexing(line);
	        			
	        			if (line.length()!=0) {
	        				if (!recordName) {
	        					// record name of book
	        					if (name.length()==0) {
	        						name = line;
	        					} else {
	        						name += " " + line;
	        					}
	        				} else if (recordName && !recordPublishDate 
	        						&& line.length() > 3 && line.substring(0,4).equals("cacm")) {
	        					publish_date = line;
	        					recordPublishDate = true;
	        				} else {
	        					// if line does not contain unneccessary information, record it in keywords field
	        					if (keywords.length()==0) {
	        						keywords = line;
	        					} else {
	        						keywords += " " + line; 
	        					}
	        				}
	        			} else {
	        				// stop recording name of book
	        				if (!name.equals("") && !recordName) {
	        					recordName = true;
	        				}
	        			}
	        			
	        			/*if (line.length()>0) {
	        				System.out.println(line);	
	        			}*/
	        		}
	        		B++;
	        		database.add(new Book(name, publish_date, keywords));
	        		fileinput.close();
	        		mybuffer.close();
	        		datainput.close();
	        	} catch (FileNotFoundException e) {
	        	} catch (IOException e) {	
	        	}
	        	
	        }
		}	
		
		/*for (int i = 0; i<database.size(); i++) {
			System.out.printf("%d %s\n", i, database.get(i).getName());
			System.out.printf("%s\n", database.get(i).getPublishDate());
			System.out.printf("%s\n\n", database.get(i).getKeywords());
		}*/
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
		/*for (String query : queries) {
			System.out.println(query);
		}*/
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
		ir.readDatabase();
		//ir.readQuery();
		
		// testing
		//String output = SearchEngine.backendIndexing(ir.readDatabase());
	}
}
