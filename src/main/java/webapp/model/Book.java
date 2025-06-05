package webapp.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private int year;
    private String level;
    private String topic;

    public Book() {}

    public Book(int id, String title, String author, int year, String level, String topic) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.level = level;
        this.topic = topic;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
}
