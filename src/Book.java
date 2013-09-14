
public class Book {
	
	
	private String name;
	private String publish_date;
	private String[] authors;
	private String description; //not compulsory
	private String[] keywords; //not compulsory
	
	public Book() {
		
	}
	
	public Book(String _name, String _publish_date, String[] _authors) {
		name = _name;
		publish_date = _publish_date;
		for (int i=0; i<_authors.length; i++) {
			authors[i] = _authors[i];
		}
	}
	
	public String getName() {
		return name;
	}
	public String getPublishDate() {
		return publish_date;
	}
	public String[] getAuthors() {
		return authors;
	}
	public String getDesription() {
		return description;
	}
	public String[] getKeywords() {
		return keywords;
	}
}
