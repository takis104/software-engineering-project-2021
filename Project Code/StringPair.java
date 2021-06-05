package schoolink;

public class StringPair {
	String id;
	String link;
	
	public StringPair(String id, String link) {
		this.id = id;
		this.link = link;
	}
	
	public String toString() {
		return "file_id :" + id + ", link: " + link;
	}
}
