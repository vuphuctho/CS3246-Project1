import java.io.IOException;
import java.util.*;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.ScoreDoc;


public class RelevanceFeedback {
	public static void getRelevance(SearchEngine instance, int query, 
			ScoreDoc[] hits, Vector<String> relevances)
			throws CorruptIndexException, IOException {
		int rel = 0;
		
		for (int i=0; i< hits.length; i++) {
			ScoreDoc hit = hits[i];
			// Document doc = hit.doc();
			Document doc = instance.searcher.doc(hits[i].doc); // This
																// retrieves
																// the
			
			String book_id = doc.get("book_id");
			if (relevances.contains(book_id)) {
				rel++;
			}
		}
		
		//System.out.println("Number of relevance docs " + rel);
		//System.out.println("Number of irrelevance docs " + (relevances.size() - rel));
	}
	
	public static boolean isRelevant(String book_id, Vector<String> relevances) {
		return relevances.contains(book_id);
	}
	
	public static void updateQuery() {
		
	}
}
