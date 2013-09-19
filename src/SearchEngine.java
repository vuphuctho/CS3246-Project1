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
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchEngine {
	
	//1. Backend Indexing
	private static HashSet<String> stop_word_set = new HashSet<String>();
	
	private static void loadStopWordList() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("src/stop_words.txt"));
		String line = null;
		while ((line = reader.readLine()) != null) {
			stop_word_set.add(line);
			//System.out.println(line);
		}
		reader.close();
	}
	
	public static String trimNumber(String input) {
		return input;
	}
	
	@SuppressWarnings("unused")
	public static String backendIndexing(String input) {
		input = input.toLowerCase();
		//loading the stop word list
		try {
			loadStopWordList();
		} catch (IOException e1) {}

		TokenStream tokenStream = new StandardTokenizer(
                Version.LUCENE_36, new StringReader(input));
        tokenStream = new StopFilter(Version.LUCENE_36, tokenStream, stop_word_set);
        tokenStream = new PorterStemFilter(tokenStream);
 
        StringBuilder sb = new StringBuilder();
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        CharTermAttribute charTermAttr = tokenStream.getAttribute(CharTermAttribute.class);
        try{
            while (tokenStream.incrementToken()) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(charTermAttr.toString());
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return sb.toString();
	}
	
	//To be continued 
	
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
		
		HashMap<String,Float> boost = new HashMap<String,Float>();
		boost.put("title", 1.3f);
		boost.put("publish_date", 1.15f);
		boost.put("keywords", 0.95f);
		boost.put("content", 0.9f);
		String[] content = new String[] {"title", "publish_date", "keywords", "content"};
		
		MultiFieldQueryParser qp = new MultiFieldQueryParser(Version.LUCENE_36, content, analyzer, boost);
		//Query q = qp.parse(Version.LUCENE_36, queryString, content, new BooleanClause.Occur[] {BooleanClause.Occur.MUST}, analyzer);
		//Query q = qp.parse(Version.LUCENE_36, new String[] {queryString}, content, analyzer);
		Query q = qp.parse(queryString);
		//QueryParser(Version.LUCENE_36, "content", new StandardAnalyzer(Version.LUCENE_36)).parse(queryString);
		
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
	
	public static void main (String[] args) {}
	
}
