package youVideo;

public class PublishableVideos extends VideoStructure{
    private String publisher;
    private String title;
    private String language;
    public PublishableVideos(String id, int duration, String url, String publisher, String title, String language) {
        super(id, duration, url);
        this.publisher = publisher;
        this.title = title;
        this.language = language;
    }

    public String getPublisher(){
        return publisher;
    }
    public String getTitle(){
        return title;
    }
    public String getLanguage(){
        return language;
    }
}
