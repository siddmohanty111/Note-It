package application;

public class JournalEntry {

	private int id;
	private String title;
	private String date;
	private String entry;

	public JournalEntry(int id, String title, String date, String entry) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.entry = entry;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

}
