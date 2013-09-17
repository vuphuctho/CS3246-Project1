/*
 * Main.java
 *
 * Created on 6 March 2006, 11:51
 *
 */



import java.util.Vector;

import lucene.demo.search.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;

public class Main {
	public static Vector<Book> database;
	public static Vector<String> queries;
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
			ScoreDoc[] hits = instance.performSearch(queries.get(0), 10);

			System.out.println("Results found: " + hits.length);
			for (int i = 0; i < hits.length; i++) {
				ScoreDoc hit = hits[i];
				// Document doc = hit.doc();
				Document doc = instance.searcher.doc(hits[i].doc); // This
																	// retrieves
																	// the

				System.out.println(doc.get("id") + " " + doc.get("name")/* + " " + doc.get("city")*/
						+ " (" + hit.score + ")");

			}
			System.out.println("performSearch done");
		} catch (Exception e) {
			System.out.println("Exception caught.\n");
			System.out.println(e.toString());
		}
		// receive relevances feedback
	}

}
