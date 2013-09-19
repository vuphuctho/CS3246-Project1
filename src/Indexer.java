/*
 * Indexer.java
 *
 * Created on 6 March 2006, 13:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class Indexer {
    
    /** Creates a new instance of Indexer */
    public Indexer() {
    }
 
    private IndexWriter indexWriter = null;
    
    public IndexWriter getIndexWriter(boolean create) throws IOException {
        if (indexWriter == null&&create) {
            
    		final File docDir = new File("index-directory");

            if (docDir.exists()) {
                System.out.println("Index at '" + docDir.getAbsolutePath() + "' is already built (delete first!)");
                System.exit(1);
            }
        	
        	FSDirectory idx = FSDirectory.open(new File("index-directory"));
    		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
    				Version.LUCENE_CURRENT, new StandardAnalyzer(Version.LUCENE_CURRENT));

    		 indexWriter = new IndexWriter(idx, indexWriterConfig);
            
        }
        return indexWriter;
   }    
   
    public void closeIndexWriter() throws IOException {
        if (indexWriter != null) {
            indexWriter.close();
        }
   }
    
    public void indexBook(int index, Book book) throws IOException {

        System.out.println("Indexing book: " + book.getBookId());
        indexWriter = getIndexWriter(false);
        Document doc = new Document();
        // need to modify format from hotel's to book's
        doc.add(new Field("id",String.valueOf(index), Field.Store.YES, Field.Index.NO));
        doc.add(new Field("book_id", book.getBookId(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("name", book.getName(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("publish_date", book.getPublishDate(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("keywords", book.getKeywords(), Field.Store.YES, Field.Index.ANALYZED));
        String fullSearchableText = book.getName() + " " + book.getPublishDate() + " " + book.getKeywords();
        doc.add(new Field("content", fullSearchableText, Field.Store.NO, Field.Index.ANALYZED));
        indexWriter.addDocument(doc);
    }   
    
    public void rebuildIndexes(Vector<Book> database) throws IOException {
          //
          // Erase existing index
          //
          getIndexWriter(true);
          //
          // Index all Accommodation entries
          //
          for(int i=0; i<database.size(); i++) {
              indexBook(i+1, database.get(i));              
          }
          //
          // Don't forget to close the index writer when done
          //
          closeIndexWriter();
     }    
    
  
    
    
}
