
public class Book {
	
	private String name;
	private String publish_date;
	//private String[] authors; //not compulsory
	//private String description; //not compulsory
	private String keywords; //not compulsory
	
	public Book() {
		this.name = "";
		this.publish_date = "";
		this.keywords = "";
	}
	
	public Book(String _name, String _publish_date, String keywords) {
		this.name = _name;
		this.publish_date = _publish_date;
		this.keywords = keywords; 
	}
	
	public String getName() {
		return name;
	}
	public String getPublishDate() {
		return publish_date;
	}
	
	public String getKeywords() {
		return keywords;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPublishDate (String publish_date) {
		this.publish_date = publish_date;
	}
	
	public void setKeywords (String keywords) {
		this.keywords = keywords;
	}
}
