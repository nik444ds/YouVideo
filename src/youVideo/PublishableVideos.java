package youVideo;
import java.util.Locale;
/*
 A class which extend VideoStructure and implements a publisher, title and languages for a video .

 */
public class PublishableVideos extends VideoStructure{
    private String publisher;
    private String title;
    private Locale language;
    public PublishableVideos(String id, int duration, String url, String publisher, String title, String language) {
        super(id, duration, url);
        this.publisher = publisher;
        this.title = title;
        this.language = new Locale(language);
    }

    public String getPublisher(){
        return publisher;
    }
    public String getTitle(){
        return title;
    }
    public Locale getLanguages(){
        return language;
    }
}
