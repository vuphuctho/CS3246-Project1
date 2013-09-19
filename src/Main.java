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
	public static Vector<Book> database  = new Vector<Book>();
	public static Vector<String> queries = new Vector<String>();
	public static Vector<Vector<String>> relevances = new Vector<Vector<String>>();
	public static Indexer indexer = new Indexer();

	public static void printResult(SearchEngine instance, ScoreDoc[] hits, boolean isNormalized, int index)
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

			if (index >= 0)
				System.out.println((i + 1) + doc.get("id") + " " + doc.get("book_id")
					+ " (" + hit.score/n + ") " + RelevanceFeedback.isRelevant(doc.get("book_id"), relevances.get(index)));
			else 
				System.out.println((i + 1) + doc.get("id") + " " + doc.get("book_id")
						+ " (" + hit.score/n + ") ");
		}
	}
	
	public static void readInputFiles() {
		// read data from input files
		InputReader ir = new InputReader();
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
			} catch (FileNotFoundException e) {}
		}
	}
	
	public static void startRebuildIndexes() {
		File docDir = new File("index-directory");

        if (docDir.exists()) {
			System.out.println("rebuildIndexes");
			try {
				indexer.rebuildIndexes(database);
			} catch (IOException e) {}
			System.out.println("rebuildIndexes done");
		}
	}
	
	public static void main(String[] args) throws Exception {
		Main.readInputFiles();
		Main.startRebuildIndexes();

		//prompt user for search id
		int querry_id = 0;
		System.out.print("Which querry would you like to search for (1-14 or enter 0 for a new querry): ");
		Scanner scan = new Scanner(System.in);
		querry_id = scan.nextInt();

		// loop
		// perform search
		// and retrieve the result
		System.out.println("performSearch");
		SearchEngine instance = new SearchEngine();
		ScoreDoc[] hits = null;
		if (querry_id == 0) {
			System.out.print("Please key in new querry: ");
			String new_querry;
			scan.nextLine();
			new_querry = scan.nextLine();
			System.out.print(new_querry);
			new_querry = SearchEngine.backendIndexing(new_querry);
			hits = instance.performSearch(new_querry, 20);

		} else if (querry_id > 0) {
			System.out.print(queries.get(querry_id - 1));
			hits = instance.performSearch(queries.get(querry_id - 1), 20);
		} else {
			System.out.print("Invalid number specified! Please try again! ");
			System.exit(0);
		}

		System.out.println("Results found: " + hits.length);

		Main.printResult(instance, hits, false, querry_id - 1);

		System.out.println("performSearch done");
		scan.close();

		// receive relevances feedback
		// RelevanceFeedback.getRelevance(instance, 0, hits, relevances.get(0));


		// receive relevances feedback

	}

}
