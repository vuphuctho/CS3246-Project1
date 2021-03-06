import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Vector;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchEngine {
	public IndexSearcher searcher = null;
	private boolean VERBOSE = true;

	/** Creates a new instance of SearchEngine */

	public SearchEngine() throws IOException {
		FSDirectory idx = FSDirectory.open(new File("index-directory"));
		searcher = new IndexSearcher(idx);

	}

	public ScoreDoc[] performSearch(String queryString, int noOfTopDocs)
			throws Exception {

		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
		
		HashMap<String,Float> boost = setBoost();
		
		String[] content = setContent();
		
		Query q = setQuery(analyzer, boost, content, queryString);
		
		TopDocs topDocs = searcher.search(q, noOfTopDocs);

		// System.out.println(topDocs);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		if (VERBOSE)
			System.out.println("Total hits in topDocs: " + topDocs.totalHits
					+ "\n");

		for (int i = 0; i < scoreDocs.length; i++) {
			Document doc = searcher.doc(scoreDocs[i].doc); // This retrieves the
		}
		
		return scoreDocs;
	}
	
	private HashMap<String,Float> setBoost() {
		HashMap<String,Float> boost = new HashMap<String,Float>();
		boost.put("title", 1.3f);
		boost.put("publish_date", 1.15f);
		boost.put("keywords", 0.95f);
		boost.put("content", 0.9f);
	
		return boost;
	}
	
	private String [] setContent() {
		return new String[] {"title", "publish_date", "keywords", "content"};
	}
	
	private Query setQuery(Analyzer analyzer, HashMap<String, Float> boost, 
			String[] content, String queryString)
					throws ParseException {
		MultiFieldQueryParser qp = new MultiFieldQueryParser(Version.LUCENE_36, content, analyzer, boost);
		return qp.parse(queryString);
	}
	
	public static void main (String[] args) {
		//for testing only
		Vector<Book> database = new Vector<Book>();
		Vector<String> queries = new Vector<String>();
		InputReader ir = new InputReader();

		if (database.size()==0) {
			System.out.println("Read database");
			database = ir.readDatabase();
		}
		if (queries.size()==0) {
			System.out.println("Read queries");
			queries = ir.readQuery();
		}
		try {
			System.out.println("performSearch");
			SearchEngine instance;
			instance = new SearchEngine();
			ScoreDoc[] hits = instance.performSearch(queries.get(0), 20);
		} catch (Exception e) {
			}	
		}
}
