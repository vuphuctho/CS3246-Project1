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
	public Vector<Book> database;
	public Vector<String> queries;
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
			//main.database = ir.readDatabase();
			main.queries = ir.readQuery();
			
			// build a lucene index
			
			/*System.out.println("rebuildIndexes");
			Indexer indexer = new Indexer();
			indexer.rebuildIndexes();
			System.out.println("rebuildIndexes done");*/

			// read query from txt files
			
			// loop
			// perform search
			// and retrieve the result
			
			/*System.out.println("performSearch");
			SearchEngine instance = new SearchEngine();
			ScoreDoc[] hits = instance.performSearch("Dame museum", 10);

			System.out.println("Results found: " + hits.length);
			for (int i = 0; i < hits.length; i++) {
				ScoreDoc hit = hits[i];
				// Document doc = hit.doc();
				Document doc = instance.searcher.doc(hits[i].doc); // This
																	// retrieves
																	// the

				System.out.println(doc.get("name") + " " + doc.get("city")
						+ " (" + hit.score + ")");

			}
			System.out.println("performSearch done");*/
		} catch (Exception e) {
			System.out.println("Exception caught.\n");
			System.out.println(e.toString());
		}
		// receive relevances feedback
	}

}
