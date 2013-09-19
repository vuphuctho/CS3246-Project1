/*
 * Main.java
 *
 * Created on 6 March 2006, 11:51
 *
 */



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.ScoreDoc;

public class Main {
	public static Vector<Book> database;
	public static Vector<String> queries;
	public static Vector<Vector<String>> relevances;
	
	public static void printResult(SearchEngine instance, ScoreDoc[] hits, boolean isNormalized)
							throws CorruptIndexException, IOException {
		// Normalized value
		double n = 1;
		
		if (isNormalized) {
			for (ScoreDoc hit : hits) {
				n = ((n > hit.score)? n : hit.score);
			}
		}
		for (int i=0; i< hits.length; i++) {
			ScoreDoc hit = hits[i];
			// Document doc = hit.doc();
			Document doc = instance.searcher.doc(hits[i].doc); // This
																// retrieves
																// the
			
			System.out.println((i + 1) + doc.get("id") + " " + doc.get("book_id")
					+ " (" + hit.score/n + ") " + RelevanceFeedback.isRelevant(doc.get("book_id"), relevances.get(0)));
		}
	}
	
	/** Creates a new instance of Main */
	public Main() {
		database = new Vector<Book>();
		queries = new Vector<String>();
		relevances = new Vector<Vector<String>>();
	}

	private void search() {
		
	}
	
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		final File docDir = new File("index-directory");
		
		
		// read data from html files
		InputReader ir = new InputReader();
		
		Main main = new Main();
		
		if (database.size()==0) {
			System.out.println("Read database");
			database = ir.readDatabase();
		}
		if (queries.size()==0) {
			System.out.println("Read queries");
			queries = ir.readQuery();
		}
		if (relevances.size()==0) {
			System.out.println("Read relevances");
			try {
				relevances = ir.readRelevance();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!docDir.exists()) {  
			try {
				// build a Lucene index
				
				System.out.println("rebuildIndexes");
				Indexer indexer = new Indexer();
				indexer.rebuildIndexes(database);
				System.out.println("rebuildIndexes done");
			} catch (Exception e) {
				System.out.println("Exception caught.\n");
				System.out.println(e.toString());
			}
		}
			// loop
			// perform search
			// and retrieve the result
			
			/*String command;
			Scanner sc = new Scanner(System.in);
			System.out.println("Input a query number (within 1 - 14) or type 0 if you want to ask a new query: ");
			
			while (sc.hasNext()) {
				command = sc.next();
				if (command.toLowerCase().equals("y")) {
					break;
				}
				
				if (command.matches("[0-9]+")) {
					int n = Integer.parseInt(command);
					if (n>0) {
						
					}
				} else {
					System.out.println("Do you want to terminate? [y/n] ");
				}
			}*/
		
			System.out.println("performSearch");
			SearchEngine instance;
			try {
				instance = new SearchEngine();
			
				ScoreDoc[] hits;
				hits = instance.performSearch(queries.get(0), 20);
				System.out.println("Results found: " + hits.length);
				
				printResult(instance, hits, false);
				

				System.out.println("performSearch done");
			
				// receive relevances feedback
				// RelevanceFeedback.getRelevance(instance, 0, hits, relevances.get(0));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
