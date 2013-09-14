import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;

import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.Version;

public class SearchEngine {
	
	//1. Backend Indexing
	private static HashSet<String> stop_word_set = new HashSet<String>();
	
	private static void loadStopWordList() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("src\\stop_words.txt"));
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
	public static void main (String[] args) {
		try {
			SearchEngine.loadStopWordList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String input = "The In I am terminated";
		System.out.print(SearchEngine.backendIndexing(input));
		
	}
}
