

import java.io.*;
import java.util.*;

public class InputReader {
	final static File data = new File("data/data_project1");
	final static File query = new File("data/CS3246Project1_query.txt");
	final static File relevance = new File("data/CS3246Project1_relevance.txt");
	//final static URL database = new URL("https://www.dropbox.com/sh/asngphsih4x5xrm/nebZcre8rF");
	
	public InputReader() {		
	}
	
	@SuppressWarnings("deprecation")
	public Vector<Book> readDatabase() {
		Vector<Book> database = new Vector<Book>();
		
		for (final File fileEntry :data.listFiles()) {
			if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	String fileName = fileEntry.getName();
	        	int pos = fileName.lastIndexOf(".");
	        	if (pos>0) {
	        		fileName = fileName.substring(0, pos);
	        	}
	        	// System.out.println(fileName);
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
	        			line = BackendIndexing.backendIndexing(line);
	        			
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
	        					// if line does not contain unnecessary information, record it in keywords field
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
	        		//System.out.println(name + " "  + publish_date);
	        		database.add(new Book(fileName, name, publish_date, keywords));
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
        			line = BackendIndexing.backendIndexing(line);
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
        		datainput.close();
			} catch (IOException e) {
			}
		} 
		/*for (String query : queries) {
			System.out.println(query);
		}*/
		return queries;
	}
	
	public Vector<Vector<String>> readRelevance() throws FileNotFoundException {
		Vector<Vector<String>> relevances = new Vector<Vector<String>>();
	
		if (relevance.isFile()) {
			FileInputStream fileinput = new FileInputStream(relevance);
    		BufferedInputStream mybuffer = new BufferedInputStream(fileinput);
    		DataInputStream datainput = new DataInputStream(mybuffer);
    		try {
    			int q=0; // no of questions 
				while (datainput.available()!= 0) {
					String[] words = datainput.readLine().split("\\s+");
					String query = words[0].substring(1);
					if (Integer.parseInt(query) > q) {
						q++;
						relevances.add(new Vector<String>());
					}
					relevances.get(q-1).add(words[1]);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/*for (int i=0; i< relevances.size(); i++) {
			System.out.println("Question " + i );
			for (int j=0; j< relevances.get(i).size(); j++) {
				System.out.println("	" + relevances.get(i).get(j));
			}
		}*/
		
		return relevances;
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
		//ir.readDatabase();
		//ir.readQuery();
		
		ir.readRelevance();
		// testing
		//String output = SearchEngine.backendIndexing(ir.readDatabase());
	}
}
