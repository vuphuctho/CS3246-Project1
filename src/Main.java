/*
 * Main.java
 *
 * Created on 6 March 2006, 11:51
 *
 */



import java.io.IOException;
import java.util.Vector;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.ScoreDoc;

public class Main {
	public static Vector<Book> database;
	public static Vector<String> queries;
	
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
			
			System.out.println(doc.get("id") + " " + doc.get("book_id")
					+ " (" + hit.score/n + ")");
		}
	}
	
	/** Creates a new instance of Main */
	public Main() {
		database = new Vector<Book>();
		queries = new Vector<String>();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		Main main = new Main();
		try {
			// read data from html files
			InputReader ir = new InputReader();
			
			if (database.size()==0) {
				System.out.println("Read database");
				database = ir.readDatabase();
			}
			if (queries.size()==0) {
				System.out.println("Read queries");
				queries = ir.readQuery();
			}
			
			// build a lucene index
			
			System.out.println("rebuildIndexes");
			Indexer indexer = new Indexer();
			indexer.rebuildIndexes(database);
			System.out.println("rebuildIndexes done");

			// loop
			// perform search
			// and retrieve the result
			
			System.out.println("performSearch");
			SearchEngine instance = new SearchEngine();
			ScoreDoc[] hits = instance.performSearch(queries.get(0), 20);

			System.out.println("Results found: " + hits.length);
			
			printResult(instance, hits, false);
			
			System.out.println("performSearch done");
		} catch (Exception e) {
			System.out.println("Exception caught.\n");
			System.out.println(e.toString());
		}
		// receive relevances feedback
	}

}
