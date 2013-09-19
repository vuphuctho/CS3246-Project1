import java.util.regex.Pattern;


public class Book {
	
	private String book_id;
	private String title;
	private String publish_date;
	//private String[] authors; //not compulsory
	//private String description; //not compulsory
	private String keywords; //not compulsory
	
	public Book() {
		this.book_id = "";
		this.title = "";
		this.publish_date = "";
		this.keywords = "";
	}
	
	public Book(String _book_id, String _title, String _publish_date, String keywords) {
		this.book_id = _book_id;
		this.title = _title;
		this.publish_date = _publish_date;
		this.keywords = keywords;
		removeNumber(keywords);
	}
	
	public String getBookId() {
		return book_id;
	}
	
	public String getTitle() {
		return title;
	}
	public String getPublishDate() {
		return publish_date;
	}
	
	public String getKeywords() {
		return keywords;
	}
	
	public void setName(String _title) {
		this.title = _title;
	}
	
	public void setPublishDate (String _publish_date) {
		this.publish_date = _publish_date;
	}
	
	public void setKeywords (String _keywords) {
		this.keywords = _keywords;
	}
	
	public void removeNumber (String k) {
		String[] words = k.split("\\s+");
		
		String newk = "";
		
		for (String word : words) {
			if (!(Pattern.matches("[a-zA-Z]+", word) == false)){
			     if (newk=="") {
			    	 newk = word;
			     } else {
			    	 newk += " " + word;
			     }
			}
			this.keywords = newk;
		}
	}
}
